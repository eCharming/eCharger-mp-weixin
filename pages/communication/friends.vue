<template>
  <view>
    <view class="navigator" :style="{'height':statusHeight+'px'}">
      <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
             @tap="back"></image>
      <text :style="{'margin-bottom':contacterBottom+'px'}">联系人</text>
    </view>
    <scroll-view
        scroll-y="true"
        scroll-with-animation=true
        style="background-color: white;"
        :style="{'height':scrollHeight+'px'}"
    >

      <view v-for="(friend,index) in friends" :key="index">
        <friendList :avatarUrl="friend.avatarUrl" :name="friend.name" :lastTime="friend.lastTime"
                    :lastWord="friend.lastWord"
                    :hasNew="friend.hasNew" :newMessageNum="friend.newMessageNum" @clickMessage="click(index)"
                    @deleteFriends="deleteFriends(index)"></friendList>
      </view>


      <view v-if="friends.length===0"
            style="width:100%;height:100%;display: flex;align-items: center;justify-content: center;flex-direction: column;">
        <image src="/static/image/blank.png" style="width: 200rpx;height: 200rpx;"></image>
        <text style="font-weight: bold;color: rgba(102,205,170,1);">还没有联系人</text>
      </view>
    </scroll-view>

  </view>

</template>

<script>
import friendList from '../../components/friendList.vue'

export default {
  components: {
    friendList,
  },
  data() {
    return {
      contacterBottom: 0,//联系人距离导航栏底部距离
      statusHeight: 0,//导航栏高度
      scrollHeight: 0,//scrollview高度
      friends: [],//所有好友数据的信息都存储在这个数组里
      uid: -1,
      statusBarHeight: uni.getSystemInfoSync().statusBarHeight,//状态栏高度

      reminders: {},
    }
  },
  methods: {
    back() {
      uni.navigateBack({})
    },

    click(index) {	//按下某一个好友 导航到chat页面 通过缓存传送好友的uid、姓名和头像url
      this.friends[index].hasNew = false;
      this.friends[index].newMessageNum = 0;
      uni.navigateTo({
        url: './chat?toUid=' + this.friends[index].uid + '&name=' + this.friends[index].name + '&avatarUrl=' + this.friends[index].avatarUrl,
      });
    },
    deleteFriends(index) {
      var uid = this.friends[index].uid;
      this.friends.splice(index, 1);
      delete this.reminders[uid];
      uni.setStorageSync(this.uid + 'friends', JSON.stringify(this.reminders));
    },
    connect() {
      this.socketTask = uni.connectSocket({		//打开链接
        url: 'wss://ws.healtool.cn/websocketapi/Reminder/' + this.uid,
        // url:'ws://127.0.0.1:8080/websocketapi/Reminder/'+this.uid,
        success: () => {

        }
      });
      this.socketTask.onMessage((res) => {	//接收到消息
        var reminders = JSON.parse(res.data); //对象化
        for (var index in reminders) {	//检索每一条数据
          var fromUid = reminders[index].fromUid;
          var text = JSON.parse(reminders[index].latestText);
          var num = reminders[index].num;
          var hasFound = false;//用于判断是否寻找到匹配的好友
          var time = this.timeObject(text.time);
          for (var i in this.friends) {
            if (fromUid == this.friends[i].uid) {	//匹配到好友的uid之后修正数据
              if (!text.isOrder) {	//不是订单
                this.friends[i].lastWord = text.message;
              } else {
                this.friends[i].lastWord = "对方发来了一个预约";
              }
              this.friends[i].newMessageNum = num;
              this.friends[i].hasNew = true;
              this.friends[i].lastTime = time;
              hasFound = true;
              break;
            }
          }
          if (hasFound == false) {	//没有匹配到好友 则好友列表当中没有该好友 向friends里加入该好友并且向缓存中注入该好友
            wx.cloud.callFunction({   //获取该好友的头像以及名字
              name: 'infoReturn',
              data: {
                uid: Number(fromUid)
              }
            }).then(
                res => {
                  var message = '';
                  if (!text.isOrder) {	//不是订单
                    message = text.message;
                  } else {
                    message = "对方发来了一个预约";
                  }
                  this.reminders[fromUid] = {			//将传入的reminder参数增加该好友
                    name: res.result.userName,
                    avatarUrl: res.result.avatarUrl,
                    time: time,
                    message: message
                  };
                  uni.setStorageSync(this.uid + 'friends', JSON.stringify(this.reminders)); 	//存入缓存持久化该好友 用于下一次打开时可以找到该好友
                  this.friends.unshift({		//向好友列表当中推入该好友
                    uid: fromUid,
                    name: res.result.userName,
                    avatarUrl: res.result.avatarUrl,
                    lastWord: message,
                    lastTime: time,
                    newMessageNum: num,//新消息数量
                    hasNew: true//是否有新消息
                  })
                }
            )

          }
        }
      });
      this.socketTask.onClose((res) => {
      });
    },
    timeObject(time) {		//用于将时间戳转化为时间显示 需要完成显示 昨天 或者日期 或者不显示日期的功能
      var currentTime = new Date();
      var currentYear = currentTime.getFullYear();
      var currentMonth = currentTime.getMonth() + 1;
      var currentDay = currentTime.getDate();
      var timeDate = new Date(time);
      var year = timeDate.getFullYear();
      var month = timeDate.getMonth() + 1;
      var day = timeDate.getDate();
      var hour = timeDate.getHours();
      var minute = timeDate.getMinutes();
      if (hour < 10)
        hour = '0' + hour;
      if (minute < 10)
        minute = '0' + minute;
      var timeText = '';
      if (currentYear == year) {
        if (currentMonth == month && currentDay == day) {
          timeText = hour + ':' + minute;
        } else if (currentMonth == month && currentDay - day == 1) {
          timeText = '昨天' + hour + ':' + minute;
        } else {
          month = (month < 10) ? '0' + month : month;
          day = (day < 10) ? '0' + day : day;
          timeText = month + '-' + day + ' ' + hour + ':' + minute;
        }
      } else {
        month = (month < 10) ? '0' + month : month;
        day = (day < 10) ? '0' + day : day;
        timeText = year + '-' + month + '-' + day + ' ' + hour + ':' + minute;
      }

      return timeText

    },
  },
  onLoad() {
    this.uid = this.$store.state.uid;
    this.statusHeight = uni.getSystemInfoSync().statusBarHeight + 50;
    this.contacterBottom = (this.statusHeight - uni.getMenuButtonBoundingClientRect().bottom);
    this.scrollHeight = uni.getSystemInfoSync().windowHeight - this.statusHeight;

  },
  onUnload() {		//页面关闭则断开连接
    if (this.socketTask != null) {
      this.socketTask.close({
        success: () => {
          this.socketTask = null;
        }
      });
    }
  },
  onShow() {
    this.friends.splice(0);   //正式版
    var reminder = uni.getStorageSync(this.uid + 'friends');		//获取好友列表缓存
    if (reminder != '') {
      this.reminders = JSON.parse(reminder);
      var keys = Object.keys(this.reminders);
      for (var index in keys) {   //正式版
        this.friends.push({
          uid: keys[index],
          name: this.reminders[[keys[index]]].name,
          avatarUrl: this.reminders[[keys[index]]].avatarUrl,
          lastWord: this.reminders[[keys[index]]].message,
          lastTime: this.timeObject(this.reminders[[keys[index]]].time),
          newMessageNum: 0,//新消息数量
          hasNew: false//是否有新消息
        })
      }

    } else this.reminders = {};
    if (this.socketTask == null) {
      this.connect();		//先注入缓存再打开websocket链接查看好友是否在我离线的时候发来了新消息
    }
  },
  onHide() {
    if (this.socketTask != null) {		//页面隐藏就关闭连接
      this.socketTask.close({
        success: () => {
          this.socketTask = null;
        }
      });
    }
  }
}
</script>

<style scoped>
.navigator {
  background-color: rgba(102, 205, 170, 1);
  display: flex;
  justify-content: center;
  align-items: flex-end;
  color: #FFFFFF;
  font-size: 35upx;
  font-weight: 700;
  letter-spacing: 2upx;
}

.friends {
  height: 150upx;
  position: relative;
}

.avatarView {
  position: absolute;
  height: 100upx;
  width: 100upx;
  border-radius: 50%;
  top: 25upx;
  left: 25upx;
  overflow: hidden;
}

.avatar {
  position: absolute;
  height: 100upx;
  width: 100upx;
}

.name {
  font-size: 35upx;
  letter-spacing: 2upx;
  position: absolute;
  top: 30upx;
  left: 150upx;
}

.lastWord {
  letter-spacing: 1upx;
  position: absolute;
  width: 500upx;
  height: 33upx;
  top: 80upx;
  left: 150upx;
  color: rgba(0, 0, 0, 0.5);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.lastTime {
  font-size: 25upx;
  letter-spacing: 1upx;
  position: absolute;
  top: 35upx;
  right: 30upx;
  color: rgba(0, 0, 0, 0.5);
}

.newMessage {
  background-color: rgba(102, 205, 170, 1);
  color: #FFFFFF;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  height: 40upx;
  width: 40upx;
  position: absolute;
  top: 80upx;
  right: 30upx;

}

.backimg {
  position: absolute;
  left: 8px;
  width: 25px;
  height: 25px;
}
</style>
