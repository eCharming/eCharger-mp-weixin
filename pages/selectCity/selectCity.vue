<template>
  <view style="display: flex;flex-direction:column">
    <view :style="{'height':statusHeight+'px','width':'100%','top':0,'z-index':9999}">
      <view class="cityNavi"
            :style="{'height':statusHeight+'px','background':color,'position':'fixed','width':'100%','top':0}">
        <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
               @tap="back"></image>
        <text :style="{'margin-bottom':cityHeight+'px'}">地点</text>
      </view>
    </view>
    <scroll-view scroll-y="true" scroll-with-animation="true" :scroll-into-view="scrollTopId"
                 :style="{'height':scrollHeight+'px'}">
      <view class="poplularwrap" id="now">
        <view class="labeltext">当前定位</view>
        <view class="nowcol">
          <view class="popular">
            <image src="../../static/image/landmark.png" style="height:40rpx;width:40upx;"></image>
            <text class='populartext'>{{ city }}</text>
          </view>
        </view>
      </view>
      <view class="poplularwrap" id="popular">
        <view class="labeltext">热门城市</view>
        <view class="popularcol">
          <view class="popular" @tap="location(0)">
            <text class='populartext'>上海</text>
          </view>
          <view class="popular" @tap="location(1)">
            <text class='populartext'>北京</text>
          </view>
          <view class="popular" @tap="location(2)">
            <text class='populartext'>深圳</text>
          </view>
        </view>
        <view class="popularcol">
          <view class="popular" @tap="location(3)">
            <text class='populartext'>广州</text>
          </view>
          <view class="popular" @tap="location(4)">
            <text class='populartext'>成都</text>
          </view>
          <view class="popular" @tap="location(5)">
            <text class='populartext'>南京</text>
          </view>
        </view>
      </view>

      <view v-for="(item,index) in obj" :key="index">
        <view :id="index">
          <citycard :alphabet="index" :list="item"></citycard>
        </view>

      </view>
    </scroll-view>
    <view class="scroll_list">
      <view style="display:flex;flex-direction: column;justify-content: space-around;"
            :style="{'height':scrollHeight-100+'px'}">
        <view id="now" class="scroll_list_item" @touchstart="getWords" @touchend="setWords">定位</view>
        <view id="popular" class="scroll_list_item" @touchstart="getWords" @touchend="setWords">热门</view>
        <view v-for="(item,index) in obj" :key="index" :id="index" class="scroll_list_item"
              @touchstart="getWords" @touchend="setWords">{{ index }}
        </view>
      </view>

    </view>
    <view :style="{'opacity':hidden}" class="showwords">
      {{ showwords }}
    </view>
  </view>
</template>

<script>
import citycard from '../../components/cityCard.vue'

export default {
  components: {
    citycard
  },
  data() {
    return {
      statusHeight: uni.getSystemInfoSync().statusBarHeight + 50,
      statusBarHeight: uni.getSystemInfoSync().statusBarHeight,
      cityHeight: 0,
      scrollHeight: 0,
      color: 'rgba(102,205,170,1)',
      obj: JSON.parse(String
          .raw`{"A": [{"name": "阿坝", "location": {"lat": 31.8994, "lng": 102.22477}}, {"name": "阿克苏", "location": {"lat": 41.16842, "lng": 80.26008}}, {"name": "阿拉善", "location": {"lat": 38.85153, "lng": 105.72898}}, {"name": "阿勒泰", "location": {"lat": 47.84564, "lng": 88.14023}}, {"name": "阿里", "location": {"lat": 30.40051, "lng": 81.1454}}, {"name": "安康", "location": {"lat": 32.68486, "lng": 109.02932}}, {"name": "安庆", "location": {"lat": 30.54294, "lng": 117.06354}}, {"name": "鞍山", "location": {"lat": 41.10777, "lng": 122.9946}}, {"name": "安顺", "location": {"lat": 26.25367, "lng": 105.9462}}, {"name": "安阳", "location": {"lat": 36.09771, "lng": 114.3931}}], "B": [{"name": "巴彦淖尔", "location": {"lat": 40.74317, "lng": 107.38773}}, {"name": "巴中", "location": {"lat": 31.86715, "lng": 106.74733}}, {"name": "巴州", "location": {"lat": 41.76404, "lng": 86.14517}}, {"name": "白城", "location": {"lat": 45.6196, "lng": 122.83871}}, {"name": "百色", "location": {"lat": 23.90216, "lng": 106.61838}}, {"name": "白山", "location": {"lat": 41.9408, "lng": 126.42443}}, {"name": "白银", "location": {"lat": 36.5447, "lng": 104.13773}}, {"name": "保定", "location": {"lat": 38.87396, "lng": 115.46459}}, {"name": "宝鸡", "location": {"lat": 34.36194, "lng": 107.23732}}, {"name": "保山", "location": {"lat": 25.11205, "lng": 99.16181}}, {"name": "包头", "location": {"lat": 40.65781, "lng": 109.84021}}, {"name": "北海", "location": {"lat": 21.48112, "lng": 109.12008}}, {"name": "北京", "location": {"lat": 39.90469, "lng": 116.40717}}, {"name": "本溪", "location": {"lat": 41.29413, "lng": 123.76686}}, {"name": "蚌埠", "location": {"lat": 32.91548, "lng": 117.38932}}, {"name": "毕节", "location": {"lat": 27.29847, "lng": 105.30504}}, {"name": "滨州", "location": {"lat": 37.38211, "lng": 117.97279}}, {"name": "亳州", "location": {"lat": 33.84461, "lng": 115.77931}}, {"name": "博州", "location": {"lat": 44.90597, "lng": 82.06665}}], "C": [{"name": "沧州", "location": {"lat": 38.30441, "lng": 116.83869}}, {"name": "长春", "location": {"lat": 43.81602, "lng": 125.32357}}, {"name": "常德", "location": {"lat": 29.03158, "lng": 111.69854}}, {"name": "昌都", "location": {"lat": 31.14073, "lng": 97.17225}}, {"name": "昌吉", "location": {"lat": 44.01117, "lng": 87.30822}}, {"name": "长沙", "location": {"lat": 28.22778, "lng": 112.93886}}, {"name": "长治", "location": {"lat": 36.19581, "lng": 113.11649}}, {"name": "常州", "location": {"lat": 31.81072, "lng": 119.97365}}, {"name": "朝阳", "location": {"lat": 41.57347, "lng": 120.4508}}, {"name": "潮州", "location": {"lat": 23.6567, "lng": 116.62296}}, {"name": "郴州", "location": {"lat": 25.77063, "lng": 113.01485}}, {"name": "承德", "location": {"lat": 40.9515, "lng": 117.9634}}, {"name": "成都", "location": {"lat": 30.5702, "lng": 104.06476}}, {"name": "赤峰", "location": {"lat": 42.2586, "lng": 118.88894}}, {"name": "池州", "location": {"lat": 30.66469, "lng": 117.49142}}, {"name": "重庆", "location": {"lat": 29.56471, "lng": 106.55073}}, {"name": "崇左", "location": {"lat": 22.37895, "lng": 107.36485}}, {"name": "楚雄", "location": {"lat": 25.04495, "lng": 101.52767}}, {"name": "滁州", "location": {"lat": 32.30181, "lng": 118.31683}}], "D": [{"name": "大理", "location": {"lat": 25.60648, "lng": 100.26764}}, {"name": "大连", "location": {"lat": 38.91369, "lng": 121.61476}}, {"name": "大庆", "location": {"lat": 46.58758, "lng": 125.10307}}, {"name": "大同", "location": {"lat": 40.07637, "lng": 113.30001}}, {"name": "大兴安岭", "location": {"lat": 51.92398, "lng": 124.59216}}, {"name": "达州", "location": {"lat": 31.20864, "lng": 107.46791}}, {"name": "丹东", "location": {"lat": 39.9998, "lng": 124.35601}}, {"name": "儋州", "location": {"lat": 19.520948, "lng": 109.580812}}, {"name": "德宏", "location": {"lat": 24.43232, "lng": 98.58486}}, {"name": "德阳", "location": {"lat": 31.12679, "lng": 104.3979}}, {"name": "德州", "location": {"lat": 37.4355, "lng": 116.35927}}, {"name": "迪庆", "location": {"lat": 27.81908, "lng": 99.70305}}, {"name": "定西", "location": {"lat": 35.58113, "lng": 104.62524}}, {"name": "东莞", "location": {"lat": 23.021016, "lng": 113.751884}}, {"name": "东营", "location": {"lat": 37.43365, "lng": 118.67466}}], "E": [{"name": "鄂尔多斯", "location": {"lat": 39.60845, "lng": 109.78087}}, {"name": "鄂州", "location": {"lat": 30.39085, "lng": 114.89495}}, {"name": "恩施", "location": {"lat": 30.27217, "lng": 109.48817}}], "F": [{"name": "防城港", "location": {"lat": 21.68713, "lng": 108.35472}}, {"name": "佛山", "location": {"lat": 23.02185, "lng": 113.12192}}, {"name": "抚顺", "location": {"lat": 41.87971, "lng": 123.95722}}, {"name": "阜新", "location": {"lat": 42.02166, "lng": 121.67011}}, {"name": "阜阳", "location": {"lat": 32.88963, "lng": 115.81495}}, {"name": "福州", "location": {"lat": 26.07421, "lng": 119.29647}}, {"name": "抚州", "location": {"lat": 27.94781, "lng": 116.35809}}], "G": [{"name": "甘南", "location": {"lat": 34.98327, "lng": 102.91102}}, {"name": "赣州", "location": {"lat": 25.83109, "lng": 114.93476}}, {"name": "甘孜", "location": {"lat": 30.04932, "lng": 101.96254}}, {"name": "高雄", "location": {"lat": 22.630576, "lng": 120.306839}}, {"name": "固原", "location": {"lat": 36.0158, "lng": 106.24259}}, {"name": "广安", "location": {"lat": 30.45596, "lng": 106.63322}}, {"name": "广元", "location": {"lat": 32.43549, "lng": 105.84357}}, {"name": "广州", "location": {"lat": 23.12908, "lng": 113.26436}}, {"name": "贵港", "location": {"lat": 23.11306, "lng": 109.59764}}, {"name": "桂林", "location": {"lat": 25.27361, "lng": 110.29002}}, {"name": "贵阳", "location": {"lat": 26.64702, "lng": 106.63024}}, {"name": "果洛", "location": {"lat": 34.47141, "lng": 100.24475}}], "H": [{"name": "哈尔滨", "location": {"lat": 45.80216, "lng": 126.5358}}, {"name": "哈密", "location": {"lat": 42.81855, "lng": 93.51538}}, {"name": "海北", "location": {"lat": 36.95454, "lng": 100.90096}}, {"name": "海东", "location": {"lat": 36.48209, "lng": 102.40173}}, {"name": "海口", "location": {"lat": 20.04422, "lng": 110.19989}}, {"name": "海南", "location": {"lat": 36.28663, "lng": 100.62037}}, {"name": "海西", "location": {"lat": 37.3771, "lng": 97.37122}}, {"name": "邯郸", "location": {"lat": 36.62556, "lng": 114.53918}}, {"name": "汉中", "location": {"lat": 33.06761, "lng": 107.02377}}, {"name": "杭州", "location": {"lat": 30.27415, "lng": 120.15515}}, {"name": "鹤壁", "location": {"lat": 35.747, "lng": 114.29745}}, {"name": "河池", "location": {"lat": 24.69291, "lng": 108.0854}}, {"name": "合肥", "location": {"lat": 31.82057, "lng": 117.22901}}, {"name": "鹤岗", "location": {"lat": 47.34989, "lng": 130.29785}}, {"name": "和田", "location": {"lat": 37.11431, "lng": 79.92247}}, {"name": "河源", "location": {"lat": 23.74365, "lng": 114.70065}}, {"name": "菏泽", "location": {"lat": 35.23363, "lng": 115.48115}}, {"name": "贺州", "location": {"lat": 24.40346, "lng": 111.56655}}, {"name": "黑河", "location": {"lat": 50.24523, "lng": 127.52852}}, {"name": "衡水", "location": {"lat": 37.73886, "lng": 115.67054}}, {"name": "衡阳", "location": {"lat": 26.89324, "lng": 112.57195}}, {"name": "红河", "location": {"lat": 23.36422, "lng": 103.3756}}, {"name": "呼和浩特", "location": {"lat": 40.84149, "lng": 111.75199}}, {"name": "葫芦岛", "location": {"lat": 40.711, "lng": 120.83699}}, {"name": "呼伦贝尔", "location": {"lat": 49.21163, "lng": 119.76584}}, {"name": "湖州", "location": {"lat": 30.89305, "lng": 120.08805}}, {"name": "花莲", "location": {"lat": 24.000674, "lng": 121.59729}}, {"name": "淮安", "location": {"lat": 33.61016, "lng": 119.01595}}, {"name": "淮北", "location": {"lat": 33.95479, "lng": 116.79834}}, {"name": "怀化", "location": {"lat": 27.56974, "lng": 110.0016}}, {"name": "淮南", "location": {"lat": 32.62549, "lng": 116.9998}}, {"name": "黄冈", "location": {"lat": 30.45347, "lng": 114.87238}}, {"name": "黄南", "location": {"lat": 35.51991, "lng": 102.01507}}, {"name": "黄山", "location": {"lat": 29.71517, "lng": 118.33866}}, {"name": "黄石", "location": {"lat": 30.19953, "lng": 115.0389}}, {"name": "惠州", "location": {"lat": 23.11075, "lng": 114.41679}}], "J": [{"name": "吉安", "location": {"lat": 27.11382, "lng": 114.99376}}, {"name": "吉林", "location": {"lat": 43.83784, "lng": 126.54944}}, {"name": "基隆", "location": {"lat": 25.122105, "lng": 121.741526}}, {"name": "济南", "location": {"lat": 36.65184, "lng": 117.12009}}, {"name": "济宁", "location": {"lat": 35.41459, "lng": 116.58724}}, {"name": "鸡西", "location": {"lat": 45.29524, "lng": 130.96954}}, {"name": "佳木斯", "location": {"lat": 46.79977, "lng": 130.31882}}, {"name": "嘉兴", "location": {"lat": 30.74501, "lng": 120.7555}}, {"name": "嘉义", "location": {"lat": 23.485079, "lng": 120.472462}}, {"name": "嘉义", "location": {"lat": 23.434473, "lng": 120.624255}}, {"name": "嘉峪关", "location": {"lat": 39.77325, "lng": 98.2882}}, {"name": "江门", "location": {"lat": 22.57865, "lng": 113.08161}}, {"name": "焦作", "location": {"lat": 35.21563, "lng": 113.24201}}, {"name": "揭阳", "location": {"lat": 23.54972, "lng": 116.37271}}, {"name": "金昌", "location": {"lat": 38.52006, "lng": 102.18759}}, {"name": "晋城", "location": {"lat": 35.49039, "lng": 112.85113}}, {"name": "金华", "location": {"lat": 29.07812, "lng": 119.64759}}, {"name": "晋中", "location": {"lat": 37.68702, "lng": 112.75278}}, {"name": "锦州", "location": {"lat": 41.09515, "lng": 121.12703}}, {"name": "景德镇", "location": {"lat": 29.26869, "lng": 117.17839}}, {"name": "荆门", "location": {"lat": 31.03546, "lng": 112.19945}}, {"name": "荆州", "location": {"lat": 30.33479, "lng": 112.24069}}, {"name": "九江", "location": {"lat": 29.70548, "lng": 116.00146}}, {"name": "酒泉", "location": {"lat": 39.73255, "lng": 98.49394}}], "K": [{"name": "喀什", "location": {"lat": 39.47042, "lng": 75.98976}}, {"name": "开封", "location": {"lat": 34.79726, "lng": 114.30731}}, {"name": "克拉玛依", "location": {"lat": 45.57999, "lng": 84.88927}}, {"name": "克州", "location": {"lat": 39.7153, "lng": 76.16661}}, {"name": "昆明", "location": {"lat": 24.87966, "lng": 102.83322}}], "L": [{"name": "拉萨", "location": {"lat": 29.64415, "lng": 91.1145}}, {"name": "来宾", "location": {"lat": 23.7521, "lng": 109.22238}}, {"name": "兰州", "location": {"lat": 36.06138, "lng": 103.83417}}, {"name": "廊坊", "location": {"lat": 39.53775, "lng": 116.68376}}, {"name": "乐山", "location": {"lat": 29.55221, "lng": 103.76539}}, {"name": "丽江", "location": {"lat": 26.85648, "lng": 100.2271}}, {"name": "丽水", "location": {"lat": 28.4672, "lng": 119.92293}}, {"name": "连云港", "location": {"lat": 34.59669, "lng": 119.22295}}, {"name": "凉山", "location": {"lat": 27.88164, "lng": 102.26746}}, {"name": "聊城", "location": {"lat": 36.45702, "lng": 115.98549}}, {"name": "辽阳", "location": {"lat": 41.26809, "lng": 123.23736}}, {"name": "辽源", "location": {"lat": 42.88805, "lng": 125.14368}}, {"name": "临沧", "location": {"lat": 23.88426, "lng": 100.08884}}, {"name": "临汾", "location": {"lat": 36.08822, "lng": 111.51962}}, {"name": "临夏", "location": {"lat": 35.60122, "lng": 103.21091}}, {"name": "临沂", "location": {"lat": 35.10465, "lng": 118.35646}}, {"name": "林芝", "location": {"lat": 29.64895, "lng": 94.36155}}, {"name": "六安", "location": {"lat": 31.73488, "lng": 116.52324}}, {"name": "六盘水", "location": {"lat": 26.59336, "lng": 104.83023}}, {"name": "柳州", "location": {"lat": 24.32543, "lng": 109.41552}}, {"name": "陇南", "location": {"lat": 33.401, "lng": 104.92166}}, {"name": "龙岩", "location": {"lat": 25.07504, "lng": 117.01722}}, {"name": "娄底", "location": {"lat": 27.69728, "lng": 111.99458}}, {"name": "泸州", "location": {"lat": 28.8717, "lng": 105.44257}}, {"name": "漯河", "location": {"lat": 33.58149, "lng": 114.01681}}, {"name": "洛阳", "location": {"lat": 34.61812, "lng": 112.45361}}, {"name": "吕梁", "location": {"lat": 37.51934, "lng": 111.14165}}], "M": [{"name": "马鞍山", "location": {"lat": 31.67067, "lng": 118.50611}}, {"name": "茂名", "location": {"lat": 21.66329, "lng": 110.92523}}, {"name": "眉山", "location": {"lat": 30.07563, "lng": 103.84851}}, {"name": "梅州", "location": {"lat": 24.28844, "lng": 116.12264}}, {"name": "绵阳", "location": {"lat": 31.46751, "lng": 104.6796}}, {"name": "苗栗", "location": {"lat": 24.696762, "lng": 120.884337}}, {"name": "牡丹江", "location": {"lat": 44.55269, "lng": 129.63244}}], "N": [{"name": "那曲", "location": {"lat": 31.47614, "lng": 92.05136}}, {"name": "南昌", "location": {"lat": 28.68202, "lng": 115.85794}}, {"name": "南充", "location": {"lat": 30.83731, "lng": 106.11073}}, {"name": "南京", "location": {"lat": 32.05838, "lng": 118.79647}}, {"name": "南宁", "location": {"lat": 22.81673, "lng": 108.3669}}, {"name": "南平", "location": {"lat": 27.33175, "lng": 118.12043}}, {"name": "南通", "location": {"lat": 31.97958, "lng": 120.89371}}, {"name": "南投", "location": {"lat": 23.919619, "lng": 120.670008}}, {"name": "南阳", "location": {"lat": 32.99073, "lng": 112.52851}}, {"name": "内江", "location": {"lat": 29.58015, "lng": 105.05844}}, {"name": "宁波", "location": {"lat": 29.87386, "lng": 121.55027}}, {"name": "宁德", "location": {"lat": 26.66571, "lng": 119.54819}}, {"name": "怒江", "location": {"lat": 25.81763, "lng": 98.8567}}], "P": [{"name": "盘锦", "location": {"lat": 41.11996, "lng": 122.07078}}, {"name": "攀枝花", "location": {"lat": 26.58228, "lng": 101.71872}}, {"name": "澎湖", "location": {"lat": 23.552351, "lng": 119.58457}}, {"name": "平顶山", "location": {"lat": 33.76609, "lng": 113.19241}}, {"name": "屏东", "location": {"lat": 22.666716, "lng": 120.492005}}, {"name": "平凉", "location": {"lat": 35.54303, "lng": 106.6653}}, {"name": "萍乡", "location": {"lat": 27.62289, "lng": 113.85427}}, {"name": "普洱", "location": {"lat": 22.82521, "lng": 100.96624}}, {"name": "莆田", "location": {"lat": 25.454, "lng": 119.00771}}, {"name": "濮阳", "location": {"lat": 35.76189, "lng": 115.02932}}], "Q": [{"name": "齐齐哈尔", "location": {"lat": 47.35431, "lng": 123.91796}}, {"name": "七台河", "location": {"lat": 45.77065, "lng": 131.00306}}, {"name": "黔东南", "location": {"lat": 26.58364, "lng": 107.98416}}, {"name": "黔南", "location": {"lat": 26.25427, "lng": 107.52226}}, {"name": "黔西南", "location": {"lat": 25.08988, "lng": 104.90437}}, {"name": "秦皇岛", "location": {"lat": 39.93545, "lng": 119.59964}}, {"name": "钦州", "location": {"lat": 21.9797, "lng": 108.65431}}, {"name": "青岛", "location": {"lat": 36.06623, "lng": 120.38299}}, {"name": "庆阳", "location": {"lat": 35.70978, "lng": 107.64292}}, {"name": "清远", "location": {"lat": 23.68201, "lng": 113.05615}}, {"name": "曲靖", "location": {"lat": 25.49002, "lng": 103.79625}}, {"name": "衢州", "location": {"lat": 28.93592, "lng": 118.87419}}, {"name": "泉州", "location": {"lat": 24.87389, "lng": 118.67587}}], "R": [{"name": "日喀则", "location": {"lat": 29.26705, "lng": 88.88116}}, {"name": "日照", "location": {"lat": 35.41646, "lng": 119.52719}}], "S": [{"name": "三门峡", "location": {"lat": 34.77261, "lng": 111.2003}}, {"name": "三明", "location": {"lat": 26.26385, "lng": 117.63922}}, {"name": "三沙", "location": {"lat": 16.83272, "lng": 112.33356}}, {"name": "三亚", "location": {"lat": 18.25248, "lng": 109.51209}}, {"name": "山南", "location": {"lat": 29.23705, "lng": 91.77313}}, {"name": "汕头", "location": {"lat": 23.3535, "lng": 116.68221}}, {"name": "汕尾", "location": {"lat": 22.78566, "lng": 115.37514}}, {"name": "上海", "location": {"lat": 31.23037, "lng": 121.4737}}, {"name": "商洛", "location": {"lat": 33.87036, "lng": 109.94041}}, {"name": "商丘", "location": {"lat": 34.41427, "lng": 115.65635}}, {"name": "上饶", "location": {"lat": 28.45463, "lng": 117.94357}}, {"name": "韶关", "location": {"lat": 24.81039, "lng": 113.59723}}, {"name": "绍兴", "location": {"lat": 30.03033, "lng": 120.5802}}, {"name": "邵阳", "location": {"lat": 27.2389, "lng": 111.4677}}, {"name": "沈阳", "location": {"lat": 41.67718, "lng": 123.4631}}, {"name": "深圳", "location": {"lat": 22.54286, "lng": 114.05956}}, {"name": "石家庄", "location": {"lat": 38.04276, "lng": 114.5143}}, {"name": "十堰", "location": {"lat": 32.62918, "lng": 110.79801}}, {"name": "石嘴山", "location": {"lat": 38.9841, "lng": 106.38418}}, {"name": "双鸭山", "location": {"lat": 46.64658, "lng": 131.1591}}, {"name": "朔州", "location": {"lat": 39.33155, "lng": 112.43286}}, {"name": "四平", "location": {"lat": 43.16646, "lng": 124.35036}}, {"name": "松原", "location": {"lat": 45.1411, "lng": 124.82515}}, {"name": "宿迁", "location": {"lat": 33.96193, "lng": 118.27549}}, {"name": "苏州", "location": {"lat": 31.29834, "lng": 120.58319}}, {"name": "宿州", "location": {"lat": 33.64614, "lng": 116.96391}}, {"name": "绥化", "location": {"lat": 46.65246, "lng": 126.96932}}, {"name": "遂宁", "location": {"lat": 30.53286, "lng": 105.59273}}, {"name": "随州", "location": {"lat": 31.69013, "lng": 113.38262}}], "T": [{"name": "塔城", "location": {"lat": 46.74532, "lng": 82.98046}}, {"name": "泰安", "location": {"lat": 36.19994, "lng": 117.0884}}, {"name": "台北", "location": {"lat": 25.030724, "lng": 121.520076}}, {"name": "台东", "location": {"lat": 22.764364, "lng": 121.113207}}, {"name": "台南", "location": {"lat": 22.998601, "lng": 120.187817}}, {"name": "太原", "location": {"lat": 37.87059, "lng": 112.55067}}, {"name": "台中", "location": {"lat": 24.143171, "lng": 120.679882}}, {"name": "泰州", "location": {"lat": 32.45546, "lng": 119.92554}}, {"name": "台州", "location": {"lat": 28.65611, "lng": 121.42056}}, {"name": "唐山", "location": {"lat": 39.63048, "lng": 118.18058}}, {"name": "桃园", "location": {"lat": 24.982757, "lng": 121.213608}}, {"name": "天津", "location": {"lat": 39.0851, "lng": 117.19937}}, {"name": "天水", "location": {"lat": 34.58085, "lng": 105.72486}}, {"name": "铁岭", "location": {"lat": 42.2862, "lng": 123.84241}}, {"name": "铜川", "location": {"lat": 34.89673, "lng": 108.94515}}, {"name": "通化", "location": {"lat": 41.72829, "lng": 125.9399}}, {"name": "通辽", "location": {"lat": 43.65247, "lng": 122.24469}}, {"name": "铜陵", "location": {"lat": 30.94486, "lng": 117.81232}}, {"name": "铜仁", "location": {"lat": 27.69066, "lng": 109.18099}}, {"name": "吐鲁番", "location": {"lat": 42.9513, "lng": 89.18954}}], "W": [{"name": "潍坊", "location": {"lat": 36.70686, "lng": 119.16176}}, {"name": "威海", "location": {"lat": 37.51348, "lng": 122.12171}}, {"name": "渭南", "location": {"lat": 34.49997, "lng": 109.51015}}, {"name": "文山", "location": {"lat": 23.39849, "lng": 104.21504}}, {"name": "温州", "location": {"lat": 27.99492, "lng": 120.69939}}, {"name": "乌海", "location": {"lat": 39.65384, "lng": 106.79546}}, {"name": "武汉", "location": {"lat": 30.59276, "lng": 114.30525}}, {"name": "芜湖", "location": {"lat": 31.35246, "lng": 118.43313}}, {"name": "乌兰察布", "location": {"lat": 40.99391, "lng": 113.13376}}, {"name": "乌鲁木齐", "location": {"lat": 43.82663, "lng": 87.61688}}, {"name": "武威", "location": {"lat": 37.9282, "lng": 102.63797}}, {"name": "无锡", "location": {"lat": 31.49099, "lng": 120.31237}}, {"name": "吴忠", "location": {"lat": 37.99755, "lng": 106.19879}}, {"name": "梧州", "location": {"lat": 23.47691, "lng": 111.27917}}], "X": [{"name": "西安", "location": {"lat": 34.34127, "lng": 108.93984}}, {"name": "锡林郭勒", "location": {"lat": 43.9332, "lng": 116.04775}}, {"name": "西宁", "location": {"lat": 36.61729, "lng": 101.77782}}, {"name": "西双版纳", "location": {"lat": 22.00749, "lng": 100.79739}}, {"name": "厦门", "location": {"lat": 24.47951, "lng": 118.08948}}, {"name": "咸宁", "location": {"lat": 29.84126, "lng": 114.32245}}, {"name": "咸阳", "location": {"lat": 34.32932, "lng": 108.70929}}, {"name": "湘潭", "location": {"lat": 27.82975, "lng": 112.94411}}, {"name": "湘西", "location": {"lat": 28.31173, "lng": 109.73893}}, {"name": "襄阳", "location": {"lat": 32.009, "lng": 112.12255}}, {"name": "孝感", "location": {"lat": 30.92483, "lng": 113.91645}}, {"name": "新北", "location": {"lat": 25.1853, "lng": 121.663675}}, {"name": "新乡", "location": {"lat": 35.30323, "lng": 113.92675}}, {"name": "信阳", "location": {"lat": 32.14714, "lng": 114.09279}}, {"name": "新余", "location": {"lat": 27.81776, "lng": 114.91713}}, {"name": "忻州", "location": {"lat": 38.4167, "lng": 112.73418}}, {"name": "新竹", "location": {"lat": 24.784924, "lng": 120.990745}}, {"name": "新竹", "location": {"lat": 24.839233, "lng": 121.002012}}, {"name": "兴安", "location": {"lat": 46.08208, "lng": 122.03818}}, {"name": "邢台", "location": {"lat": 37.07055, "lng": 114.50443}}, {"name": "许昌", "location": {"lat": 34.0357, "lng": 113.85233}}, {"name": "徐州", "location": {"lat": 34.2044, "lng": 117.28577}}, {"name": "宣城", "location": {"lat": 30.94078, "lng": 118.75866}}], "Y": [{"name": "雅安", "location": {"lat": 30.01053, "lng": 103.0424}}, {"name": "延安", "location": {"lat": 36.58529, "lng": 109.48978}}, {"name": "延边", "location": {"lat": 42.89119, "lng": 129.5091}}, {"name": "盐城", "location": {"lat": 33.34951, "lng": 120.16164}}, {"name": "烟台", "location": {"lat": 37.46353, "lng": 121.44801}}, {"name": "阳江", "location": {"lat": 21.85829, "lng": 111.98256}}, {"name": "阳泉", "location": {"lat": 37.85668, "lng": 113.58047}}, {"name": "扬州", "location": {"lat": 32.39358, "lng": 119.41269}}, {"name": "宜宾", "location": {"lat": 28.7513, "lng": 104.6417}}, {"name": "宜昌", "location": {"lat": 30.69186, "lng": 111.28642}}, {"name": "伊春", "location": {"lat": 47.72752, "lng": 128.84049}}, {"name": "宜春", "location": {"lat": 27.81443, "lng": 114.41612}}, {"name": "宜兰", "location": {"lat": 24.759707, "lng": 121.754442}}, {"name": "伊犁", "location": {"lat": 43.91689, "lng": 81.32416}}, {"name": "益阳", "location": {"lat": 28.55391, "lng": 112.35516}}, {"name": "银川", "location": {"lat": 38.48644, "lng": 106.23248}}, {"name": "营口", "location": {"lat": 40.66683, "lng": 122.2349}}, {"name": "鹰潭", "location": {"lat": 28.26019, "lng": 117.06919}}, {"name": "永州", "location": {"lat": 26.42034, "lng": 111.61225}}, {"name": "玉林", "location": {"lat": 22.65451, "lng": 110.18098}}, {"name": "榆林", "location": {"lat": 38.2852, "lng": 109.73458}}, {"name": "玉树", "location": {"lat": 33.00528, "lng": 97.0065}}, {"name": "玉溪", "location": {"lat": 24.3518, "lng": 102.54714}}, {"name": "岳阳", "location": {"lat": 29.35728, "lng": 113.12919}}, {"name": "运城", "location": {"lat": 35.02628, "lng": 111.00699}}, {"name": "云浮", "location": {"lat": 22.91525, "lng": 112.04453}}, {"name": "云林", "location": {"lat": 23.664943, "lng": 120.480738}}], "Z": [{"name": "枣庄", "location": {"lat": 34.81071, "lng": 117.32196}}, {"name": "湛江", "location": {"lat": 21.27134, "lng": 110.35894}}, {"name": "彰化", "location": {"lat": 24.068523, "lng": 120.557479}}, {"name": "张家界", "location": {"lat": 29.11667, "lng": 110.47839}}, {"name": "张家口", "location": {"lat": 40.82444, "lng": 114.88755}}, {"name": "张掖", "location": {"lat": 38.92592, "lng": 100.44981}}, {"name": "漳州", "location": {"lat": 24.51347, "lng": 117.64725}}, {"name": "肇庆", "location": {"lat": 23.0469, "lng": 112.46528}}, {"name": "昭通", "location": {"lat": 27.33817, "lng": 103.7168}}, {"name": "镇江", "location": {"lat": 32.18959, "lng": 119.425}}, {"name": "郑州", "location": {"lat": 34.74725, "lng": 113.62493}}, {"name": "中山", "location": {"lat": 22.517024, "lng": 113.392517}}, {"name": "中卫", "location": {"lat": 37.50026, "lng": 105.19676}}, {"name": "周口", "location": {"lat": 33.62583, "lng": 114.69695}}, {"name": "舟山", "location": {"lat": 29.98539, "lng": 122.20778}}, {"name": "珠海", "location": {"lat": 22.27073, "lng": 113.57668}}, {"name": "驻马店", "location": {"lat": 33.01142, "lng": 114.02299}}, {"name": "株洲", "location": {"lat": 27.82767, "lng": 113.13396}}, {"name": "淄博", "location": {"lat": 36.8131, "lng": 118.0548}}, {"name": "自贡", "location": {"lat": 29.3392, "lng": 104.77844}}, {"name": "资阳", "location": {"lat": 30.12859, "lng": 104.62798}}, {"name": "遵义", "location": {"lat": 27.72545, "lng": 106.92723}}]}`
      ),
      scrollTopId: "",
      hidden: 0,
      showwords: "",
      popluarLoc: [{
        name: "上海",
        lat: 31.23037,
        lng: 121.4737
      },
        {
          name: "北京",
          lat: 39.90469,
          lng: 116.40717
        },
        {
          name: "深圳",
          lat: 22.54286,
          lng: 114.05956
        },
        {
          name: "广州",
          lat: 23.12908,
          lng: 113.26436
        },
        {
          name: "成都",
          lat: 30.5702,
          lng: 104.06476
        },
        {
          name: "南京",
          lat: 32.05838,
          lng: 118.79647
        }
      ]
    }
  },
  methods: {
    back() {
      uni.navigateBack({})
    },
    getWords(e) {
      var id = e.target.id;
      if (id == 'popular')
        id = "热"
      else if (id == 'now')
        id = "定"
      this.showwords = id;
      this.hidden = 1;
    },
    setWords(e) {
      var id = e.target.id;
      this.scrollTopId = id;
      this.hidden = 0;

    },
    location(index) {
      let poploc = this.popluarLoc[index];
      this.$store.commit('setCityLocation', poploc)
      uni.navigateBack({})
    }
  },
  computed: {
    city() {
      let city = this.$store.state.city;
      if (city == "") {
        city = "未定位";
      }
      return city;
    }
  },
  mounted() {
    this.cityHeight = (this.statusHeight - uni.getMenuButtonBoundingClientRect().bottom);
    uni.getSystemInfo({
      success: (res) => {
        this.scrollHeight = res.windowHeight - this.statusHeight;
      }
    })
  }
}
</script>

<style scoped>
.cityNavi {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  color: #FFFFFF;
  font-size: 35upx;
  font-weight: 700;
  letter-spacing: 2upx;
}

.backimg {
  position: absolute;
  left: 8px;
  width: 25px;
  height: 25px;
}

.scroll_list {
  background: rgba(0, 0, 0, 0);
  position: fixed;
  width: 55rpx;
  top: 200rpx;
  right: 20rpx;

}

.scroll_list_item {
  text-align: center;
  font-size: 25rpx;
  color: rgb(99, 99, 99);
  margin: 8rpx;
  width: 100%;
  height: 100%;
}

.showwords {
  width: 80px;
  height: 80px;
  background: rgba(0, 0, 0, .3);
  border-radius: 50%;
  line-height: 80px;
  text-align: center;
  font-size: 10vw;
  margin: auto;
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 999;
  color: #ffffff;
}

.labeltext {
  letter-spacing: 2upx;
  font-weight: 700;
  color: #666666;
}

.poplularwrap {
  margin: 20rpx;
  padding: 15upx;
}

.populartext {
  text-align: center;
  letter-spacing: 3upx;
  font-weight: 700;
}

.popular {
  background: white;
  border-radius: 10upx;
  width: 200rpx;
  height: 70rpx;
  box-shadow: 0upx 12upx 24upx 8upx #e5e5e5;
  display: flex;
  justify-content: center;
  align-items: center;
}

.popularcol {
  display: flex;
  justify-content: space-between;
  margin-top: 20rpx;
}

.nowcol {
  display: flex;
  justify-content: flex-start;
  margin-top: 20rpx;
}
</style>
