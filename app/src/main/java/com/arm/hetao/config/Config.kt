package com.arm.hetao.config

/**
 *    author : heyueyang
 *    time   : 2023/01/16
 *    desc   :
 *    version: 1.0
 */
interface Config {

    interface DeviceInfo {
        companion object {
            const val DeviceId = "c79879c0-38a5-4785-9be4-0aab0eaef2d3"
            const val Id = "18612335771"
            const val Password = "123456"
        }
    }

    interface Http {
        companion object {
            const val PARAMS_KEY = "http_params"
            const val BASE_VIDEO_ULR = "http://imgres-cdn.chenlongsoft.com/GW_RES/static/"

            //child_id=0d2ac105-6327-4568-bc06-042f998858b0&count=10&newTime=0&s_id=-945-91-412411412249120-13-91118-886663104&startpage=0
            const val VIDEO_URL = "http://www.chenlongsoft.com:8091/rest/circle/query/exchangelist4"

            //date=2023-07-26&ouId=551A7EAD59DFF6D9AE686694F17D4E20A58107181DF2FF0122B7C7FF7A649065&sid=-945-91-412411412249120-13-91118-886663104
            const val MENU_URL = "http://www.chenlongsoft.com:8091/rest/food/query/day/maa"


            const val UPDATE_URL = "https://www.pgyer.com/apiv2/app/install?_api_key=285c614743cd04a463caa5f8f96ecf36&appKey=5b9642001395246f7d7626929599dc1a"

        }
    }


}