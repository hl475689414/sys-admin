function getCascaderObj(val,opt) {
    return val.map(function (value, index, array) {
        for (var itm of opt) {
            if (itm.value == value) { opt = itm.children; return itm; }
        }
        return null;
    });
}

// var jobs=$("#jobClassList").val();//获取所有招聘职位
// var citys=$("#citys").val();//获取所有工作地区

//为招聘职位下拉框赋值
var jobClass = new Vue({
    el: '#jobClass',
    data:{
        jobClasss:[],
        // jobClass: [(),()],
        jobClass: [ Number($("#firstClassId").val()), Number($("#classId").val())],
        options:JSON.parse($("#jobClassList").val())
    },
    methods: {
        handleItemChange() {
            this.jobClasss=getCascaderObj(this.jobClass, this.options);
        }
    }
})
//为工作地区下拉框赋值
var assd = new Vue({
    el: '#cityList',

    data:{
        cityss:[],
        citys: [Number($("#firstWorkCity").val()),Number($("#workCity").val())],
        options:JSON.parse($("#citys").val())
    },
    methods: {
        handleItemChange() {
            this.cityss=getCascaderObj(this.citys, this.options);
        }
    }
})
//为工作经验下拉框赋值
var workYear = new Vue({
    el: '#workYear',

    data:{
        workYears:[],
        workYear: [$("#_workYear").val()],
        options:[
            {value:'2', label:'1年以下'}
            ,{value:'3', label:'1-3年'}
            ,{value:'4', label:'3-5年'}
            ,{value:'5', label:'5-10年'}
            ,{value:'6', label:'10年以上'}
        ]
    },
    methods: {
        handleItemChange() {
            this.workYears=getCascaderObj(this.workYear, this.options);
        }
    }
})
//为会员等级下拉框赋值
var ispush = new Vue({
    el: '#ispush',

    data:{
        ispushs:[],
        ispush: [$("#push").val()],
        options:[
            {value: '0', label: '否'}
            ,{value:'1', label:'是'}
        ]
    },
    methods: {
        handleItemChange() {
            this.ispushs=getCascaderObj(this.ispush, this.options);
        }
    }

})
