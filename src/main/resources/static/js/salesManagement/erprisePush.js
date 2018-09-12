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
var citys = new Vue({
    el: '#cityList',

    data:{
        cityss:[],
        // citys: [$("#firstWorkCity").val(),$("#workCity").val()],
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
            {value: '1', label: '应届生'}
            ,{value:'2', label:'1年以下'}
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
var grade = new Vue({
    el: '#grade',

    data:{
        grades:[],
        grade: [$("#rank").val()],
        options:[//普通会员、银卡会员、金卡会员、铂金会员
            {value: '0', label: '普通会员'}
            ,{value:'1', label:'银卡会员'}
            ,{value:'2', label:'金卡会员'}
            ,{value:'3', label:'铂金会员'}
        ]
    },
    methods: {
        handleItemChange() {
            this.grades=getCascaderObj(this.grade, this.options);
        }
    }
})
//为排序下拉框赋值
var sort = new Vue({
    el: '#sort',

    data:{
        sorts:[],
        sort: [$("#_sort").val()],
        options:[//上次登录时间（默认）、发布时间
            {value: '1', label: '上次登录时间'}
            ,{value:'2', label:'发布时间'}
        ]
    },
    methods: {
        handleItemChange() {
            this.sorts=getCascaderObj(this.sort, this.options);
        }
    }
})