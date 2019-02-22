/**
 * @author: Dobility
 * @time: 2017年3月12日22:00:41
 * Vue组件：带有加减控件的数字输入框
 */
Vue.component('input-number', {
    props: {
        max: {
            default: 10000          // 最大值
        }, min: {
            default: 1              // 最小值
        }, val: {
            default: 1              // 初始值
        }, step: {
            default: 1              // 加减的增量
        }, size: {
            default: ""             // 组件规模大小
        }, id: {
            default: ""             // input的id
        }, name: {
            default: ""             // input的name
        }, readonly: {
            default: false         // 是否readonly
        }, accept: {
            default: "int"          // 接受整型还是浮点型
        }, theme: {
            default: ""             // 主题，bootstrap型（默认），圆形
        }, color: {
            default: ""             // 颜色，浅灰（默认），橙色，绿色
        }, dataParam: {
            default: "0"            // 给子组件传入了参数，可以后期返回
        }
    },
    template: '<div class="am-input-group input-number-box">' +
                   '<span class="am-input-group-label btn-minus" @click="minus">-</span>' +
                   '<input class="am-form-field am-text-center input-number"\
                          :id="id" type="number" :name="name" :readonly="readonly"\
                          :value="number"\
                          @change="updateValue($event.target.value)">' +
                   '<span class="am-input-group-label btn-plus" @click="plus">+</span>\
               </div>',
    data: function() {
        return {
            number: this.val,
            param: (this.dataParam + "").split(/, */).map(function(e) {
                return eval(e);
            })     // 转化参数数组元素为正常的数据类型
        }
    },
    created: function () {
        this.$emit('get-data-param', this.param, this.number);
    },
    methods: {
        minus: function () {
            if (this.number - this.step > this.min) {
                this.number -= this.step;
            } else {
                this.number = this.min;
                $(this.$el).find(".btn-minus").addClass("disabled");
            }
            $(this.$el).find(".btn-plus").removeClass("disabled");
            this.$emit('get-value', this.number);
            this.$emit('get-data-param', this.param, this.number);
        },
        plus: function () {
            if (this.number + this.step < this.max) {
                this.number += this.step;
            } else {
                this.number = this.max;
                $(this.$el).find(".btn-plus").addClass("disabled");
            }
            $(this.$el).find(".btn-minus").removeClass("disabled");
            this.$emit('get-value', this.number);
            this.$emit('get-data-param', this.param, this.number);
        },
        updateValue: function (v) {
            if (this.accept == "double") {
                v = parseFloat(v);
            } else {
                v = parseInt(v);
            }
            if (isNaN(v)) {
                v = this.val;
            } else if (v > this.max) {
                v = this.max;
            } else if (v < this.min) {
                v = this.min;
            }
            $(this.$el).find("input").val(v);
            this.number = v;
            // this.$refs.input.value = v;
            this.$emit('input', this.number);         // 这一句使得 v-model 起作用
            this.$emit('get-value', this.number);
            this.$emit('get-data-param', this.param, this.number);
        }
    }
});