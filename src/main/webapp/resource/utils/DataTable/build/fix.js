// oSort是排序类型数组, 'chinese-asc'是自己定义的类型的排序(*-asc || *-desc)名称
// 插件应该会根据表格中的内容的类型(string, number, chinese)进行比较排序，
// 如果以chinese类型来排序则用oSort['chinese-asc']和oSort['chinese-desc']的方法
// oSort对应的function里面自定义比较方法
jQuery.fn.dataTableExt.oSort['chinese-asc'] = function(x,y) {
    //javascript自带的中文比较函数，具体用法可自行查阅了解
    return x.localeCompare(y);
};
 
jQuery.fn.dataTableExt.oSort['chinese-desc'] = function(x,y) {
    return y.localeCompare(x);
};

// 修改出错提示为 console
jQuery.fn.dataTableExt.sErrMode = "console";

// aTypes是插件存放表格内容类型的数组
// reg赋值的正则表达式，用来判断是否是中文字符
// 返回值push到aTypes数组，排序时扫描该数组，'chinese'则调用上面两个方法。返回null默认是'string'
jQuery.fn.dataTableExt.aTypes.push(function(sData) {
    var reg =/^[\u4e00-\u9fa5]{0,}$/;
    if(reg.test(sData)) {
        return 'chinese';
    }
    return null;
});
