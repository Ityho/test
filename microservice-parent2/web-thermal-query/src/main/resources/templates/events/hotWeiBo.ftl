<#include "../common/resourcePath.ftl"/>
<style>
    .text-view{
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;  //需要显示时文本行数
    overflow: hidden;
    }
</style>

	<div class="w-navigation">
        <div class="group-item bgTrans">
            <ul>
                <li><span>微博类型:</span> <a
                        :class="activeTypeClass == hcd.name ? 'active':''"
                        href="javascript:;" v-for="(hcd,index) in hotClassData"
                        v-text="hcd.name" @click="changeParm(hcd)"></a></li>
            </ul>
        </div>

    </div>
	<div>
        <table class="w-table" border="0" cellspacing="0" cellpadding="0">
            <thead>
            <tr>
                <th width="5%">
                    <div>排名</div>
                </th>
                <th>
                    <div>热门微博</div>
                </th>
                <th width="10%">
                    <div>热度值</div>
                </th>
                <th width="15%">
                    <div>更多操作</div>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in list">
                <td v-text="index+1"></td>
                <td>
                    <div class="w-table-item">
                        <div class="card-avatar margin-left-0 margin-bottom-10">
                            <div class="avatar avatar-lg margin-0"
                                 @click="weiboDetail(item.url)">
                                <img v-bind:src="item.user.profile_image_url" alt="">
                            </div>
                            <div class="inline-block margin-left-10 text-left">
                                <p class="margin-bottom-5 font-size-16 color_11"
                                   v-text="item.user.name">微博昵称</p>
                                <p class="font-size-12">{{item.orig_created_at |
                                    formatDate('YYYY-MM-DD')}}</p>
                            </div>
                        </div>
                        <p class="check-text">
                            <span  v-html="item.text" class="weibo_a">  </span>
                            <a onclick="handleInfoShow(this)"  href="javascript:;" class="color_11 btn-check" data-index="1">查看详情<i class="icon icon-open"></i></a>
                        </p>

                        <!-- 							<p class="check-text text-view" v-show="!item.openFlag && !item.extendFlag" style="height:70px;"> -->
                        <!--                                  <span v-html="item.text" ></span> -->
                        <!--                                  <a @click="handleInfoShow(item)" href="javascript:;" class="color_11 btn-check" data-index="1" v-if="item.openFlag==false" >查看详情<i class="icon icon-open"></i></a> -->
                        <!--                              </p> -->
                        <!--                              <p class="check-text" v-if="item.extendFlag==true"  v-show="item.extendFlag"> -->
                        <!--                                  <span v-html="item.text" ></span> -->
                        <!--                              </p> -->


                        <!--                              <p class="check-content"  v-if="item.openFlag==true" v-show="item.openFlag" > -->
                        <!--                                  <span v-html="item.text"></span> -->
                        <!--                                  <a href="javascript:;" @click="handleInfoShow(item)" class="color_11 btn-check" data-index="2">收起详情<i class="icon icon-shouqi"></i></a> -->
                        <!--                              </p> -->


                        <!-- 							<p v-html="item.text" ></p> -->
                        <p class="color_5 margin-top-25">
                            <span v-text="'转发：'+item.reposts_count"></span> <span
                                class="margin-left-40"
                                v-text="'评论：'+item.comments_count"></span> <span
                                class="margin-left-40"
                                v-text="'点赞：'+item.attitudes_count"></span>
                        </p>
                    </div>
                </td>
                <td class="line-right" v-text="item.hot"></td>
                <td><a href="#" @click="createAnalysis(item.url)"
                       class="a-hover-yellow">创建分析</a></td>
            </tr>
            </tbody>
        </table>
        <div class="color_5 font-size-14 text-center padding-vertical-15">
            <div class="inline-block w-ball-clip-rotate la-sm v_a_m">
                <div></div>
            </div>
            加载更多
        </div>
    </div>
<script type="text/javascript">
    function handleInfoShow(object){
        debugger;
        if($(object).attr('data-index')==1){
            $(object).parent().find('span').css('height','initial');
            $(object).html('收起详情<i class="icon icon-shouqi"></i>')
            $(object).attr('data-index',2)
        }else{
            $(object).parent().find('span').css('height','90px')
            $(object).html('查看详情<i class="icon icon-open"></i>')
            $(object).attr('data-index',1)
        }
    }
    $('.btn-check').on('click',function () {

    })

    var categoryId="${categoryId}";
    function goSingleWeiboAnalysis(url){
        if("${admin.userId}">0){
            window.open("${njxBasePath}/i/singleWeiboAnalysis/index.shtml?rankWeiBoUrl="+ url, "_blank");
        } else {
            $("#loginModal").modal();
        }
    }
    function GotoPage(i) {
        var flag;
        var obj;
        $("#smallClassUl label").each(function() {
            if ($(this).attr("class").indexOf('active') != -1) {
                obj = $(this);
                flag = $(this).find("input").val();
            }
        });

        $("#page").val(i);
        //         findKeyword(flag,obj,i);
        selectSearchList();
    }

    $(function() {
        if (1 == '${numb}') {
            $("#pageSizeTotal").hide();
            $("#loadMoreId").show();
        } else {
            $("#pageSizeTotal").show();
            $("#loadMoreId").hide();
        }
        $(".sharesList ul li").on("click", function() {
            var num = $("#checkNum").text();
            num = parseInt(num);
            if ($(this).hasClass("active")) {
                $(this).toggleClass("active");
                num--;
            } else {
                var checkID = [];
                $.each($("#sharesList li"), function (n) {
                    if ($(this).hasClass("active")) {
                        checkID.push(n);
                    }
                });

                if (checkID.length < 2) {
                    $(this).toggleClass("active")
                    num++;
                }
            }
            $("#checkNum").text(num);
        });
    });
</script>
<script type="text/javascript" src="${staticResourcePath}/js/jquery.endless-scroll-1.3.js"></script>


<script type="text/javascript" charset="gbk" src="${staticResourcePath}/js/utils/utils.js?v=${sysConfig}"></script>
<script type="text/javascript"  charset="utf-8" src="${staticResourcePath}/js/home/hotWeibo.js?v=${sysConfig}"></script>




