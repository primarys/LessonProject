new Vue({el:"#c-app",data:{newPassword:"",again:"",shrinked:"true"==getCookie("shrinked"),stuList:[],stuListText:"",stuListInputStatus:"",stuListCorrect:!1},methods:{saveStudent:function(){event.preventDefault();var b=AMUI.dialog.loading({title:"\u6b63\u5728\u4e0a\u4f20"}),c=new FormData($("#ajax-form")[0]);$.ajax({type:"POST",data:c,url:"saveStudent",contentType:!1,processData:!1,error:function(){b.modal("close");errorTip()},success:function(a){b.modal("close");"false"!=a?AMUI.dialog.alert({title:"\u64cd\u4f5c\u6210\u529f",
                content:"\u5df2\u6210\u529f\u4fdd\u5b58\u5b66\u751f",onConfirm:function(){location.href="viewLesson?id="+$("[name=vid]").val()}}):errorTip()}})},deleteStudent:function(b,c){AMUI.dialog.confirm({title:"\u786e\u8ba4\u5220\u9664\u8be5\u5b66\u751f\uff1f",content:"\u5220\u9664\u540e\u8be5\u5b66\u751f\u4ee5\u53ca\u70b9\u540d\u8bb0\u5f55\u5c06\u88ab\u5220\u9664\uff0c\u8bf7\u8c28\u614e\u64cd\u4f5c",onConfirm:function(){$.post("deleteStudent",{vid:b,cid:c},function(a){a?AMUI.dialog.alert({title:"\u5220\u9664\u6210\u529f",
                content:"\u8be5\u5b66\u751f\u4ee5\u53ca\u70b9\u540d\u8bb0\u5f55\u4e5f\u88ab\u5220\u9664",onConfirm:function(){location.href="viewLesson?id="+b}}):errorTip()},"json")}})},shrink:function(){null==getCookie("shrinked")||"false"==getCookie("shrinked")?addCookie("shrinked","true"):addCookie("shrinked","false");$("div.ifr-js-shrink").slideToggle(200);$("img.ifr-js-shrink").toggleClass("ifr-img-head-sm");$(".ifr-div-shrink button").eq(0).toggle();$(".ifr-div-shrink button").eq(1).toggle()},translate:function(){var b=
            this.stuListText.trim().replace(/\t/gm," ").split("\n");this.stuList=[];for(var c=0;c<b.length;c++){var a=b[c].split(/ +/);if(3==a.length&&("\u7537"==a[2]||"\u5973"==a[2]))this.stuList.push({num:a[0],name:a[1],sex:"\u7537"==a[2]?1:2});else if(1!=a.length||""!=a[0]){this.stuList=[];AMUI.dialog.alert({title:"\u64cd\u4f5c\u5931\u8d25",content:"\u683c\u5f0f\u9519\u8bef"});return}}0<this.stuList.length&&(this.stuListCorrect=!0,this.stuListInputStatus="\u8f93\u5165"+b.length+"\u884c\uff0c\u8f6c\u6362\u6210\u529f"+
            this.stuList.length+"\u884c")},saveStudents:function(){event.preventDefault();var b=AMUI.dialog.loading({title:"\u6b63\u5728\u4e0a\u4f20"}),c=new FormData($("#ajax-form")[0]);$.ajax({type:"POST",data:c,url:"saveStudents",contentType:!1,processData:!1,error:function(){b.modal("close");errorTip()},success:function(a){b.modal("close");"false"!=a?AMUI.dialog.alert({title:"\u64cd\u4f5c\u6210\u529f",content:"\u5df2\u6210\u529f\u4fdd\u5b58\u5b66\u751f",onConfirm:function(){location.href="viewLesson?id="+
                    $("[name=vid]").val()}}):errorTip()}})},changeRollState:function(b,c,a){$.post("modifyAttendance",{state:b,rollCallId:c},function(c){$("#changeRollStateDropdown"+a).dropdown("close");1==b&&($("#rollIndex"+a).html('<span class="awardorstate-show presense-bg am-text-white" style="height: 100%">\u51fa\u5e2d</span>'),$("#rollIndexDropdown"+a).html('<button class="am-btn am-btn-success" style="height: 100%;"><span class="am-icon-caret-down"></span></button>'));2==b&&($("#rollIndex"+a).html('<span class="awardorstate-show leave-bg am-text-white" style="height: 100%">\u8bf7\u5047</span>'),
            $("#rollIndexDropdown"+a).html('<button class="am-btn am-btn-warning leave-bg" style="height: 100%;"><span class="am-icon-caret-down"></span></button>'));3==b&&($("#rollIndex"+a).html('<span class="awardorstate-show late-bg am-text-white" style="height: 100%">\u8fdf\u5230</span>'),$("#rollIndexDropdown"+a).html('<button class="am-btn late-bg late-caret-down" style="height: 100%;"><span class="am-icon-caret-down"></span></button>'));4==b&&($("#rollIndex"+a).html('<span  class="awardorstate-show absense-bg am-text-white" style="height: 100%">\u7f3a\u5e2d</span>'),
            $("#rollIndexDropdown"+a).html('<button class="am-btn am-btn-danger" style="height: 100%;"><span class="am-icon-caret-down"></span></button>'));$("#"+a+"rollCallIndex1").html(c[0]);$("#"+a+"rollCallIndex2").html(c[1]);$("#"+a+"rollCallIndex3").html(c[2]);$("#"+a+"rollCallIndex4").html(c[3])},"JSON")},changeQuizAward:function(b,c){var a=$("#AwardInput"+c).val();/^[0-9]*[1-9][0-9]*$/.test(a)?$.post("modifyQuiz",{quizId:b,award:a},function(b){$("#changeQuizAwardDropdown"+c).dropdown("close");$("#quizIndex"+
            c).html("+"+a);$("#quizAwardIndex"+c).html(b)},"JSON"):alert("\u8bf7\u8f93\u5165\u6b63\u6574\u6570")}}});function errorTip(){AMUI.dialog.alert({title:"\u64cd\u4f5c\u5931\u8d25",content:"\u8bf7\u91cd\u8bd5"})};