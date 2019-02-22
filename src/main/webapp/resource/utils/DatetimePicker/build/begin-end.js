$(function () {
    // 关联日期
    var $begin = $('#beginTimePicker');
    var $end = $('#endTimePicker');
    if ($begin.length > 0 && $end.length > 0) {
        $begin.datetimepicker();
        $end.datetimepicker({
            useCurrent: false
        });
        $begin.on('dp.change', function(e) {
            $end.data('DateTimePicker').minDate(e.date);
        });
        $end.on('dp.change', function(e) {
            $begin.data('DateTimePicker').maxDate(e.date);
        });
    }
});