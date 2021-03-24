// js开发中常用的小技巧

// 获取指定范围内的随机数
export function getRadomNum(min,max){
    return Math.floor(Math.random()*(max - min + 1) + min)
}
// 随机获取数组中的元素
export function getRadomFromArr(arr){
    return arr[Math.floor(Math.random()*arr.length)];
}

// 生成从0到指定值的数字数组
export function getArray(len){
    var arr = [], i=1;
    for(;i<len;i++){
        arr.push(i)
    }
    return arr
}

// 对象转换为数组
//注意对象必须是以下格式的才可以通过此方式转化为数组
//获取的DOM集合，以及函数的arguments也可以通过此方式转化为数组
var obj = {
    0: 'qian',
    1: 'long',
    2: 'chu',
    3: 'tian',
    length: 4
 
}
var objArr = Array.prototype.slice.call(obj);
// var objArr = [].slice.call(obj);
// var objArr = Array.prototype.slice.apply(obj);
console.log(objArr)

// 打乱数字数组的顺序
var arr = [1, 2, 3, 4, 5, 6, 7, 'a', 'dsfs', 8, 9, 'v'];
arr.sort(function() {
    return Math.random() - 0.5
});

// 获得数组中最大或者最小值
export function maxAndMin(arr){
    return {
       max:Math.max.apply(null,arr.join(',').split(',')),
       min:Math.min.apply(null,arr.join(',').split(','))
    }
}

// 保留指定小数位
var num =4.345678;
num = num.toFixed(4);  // 4.3457 第四位小数位以四舍五入计算

// 生成指定长度的随机字母数字字符串
// 生成指定长度的随机字母数字字符串
/*写法一
function getRandomStr(len) {
    var str = "";
    for (; str.length < len; str += Math.random().toString(36).substr(2));
    return str.substr(0, len);
}
写法二
function getRandomStr(len) {
    var str = "";
    for (; str.length < len;) {
        str += Math.random().toString(36).substr(2);
    }
    return str.substr(0, len);
}*/
/*写法三*/
function getRandomStr(len) {
    for (var str = ''; str.length < len; str += Math.random().toString(36).substr(2));
    return str.substr(0, len)
}

// 找到数组中出现次数最多的元素,并给出其出现过的位置
export function getMaxAndIndex(arr) {
    var obj = {};
    arr.forEach(function(item, index) {
        if (!obj[item]) {
            obj[item] = {
                indexs: [index]
            }
        } else {
            obj[item]['indexs'].push(index);
        }
    });
    var num = 0; //记录出现次数最大值
    var str = ''; //记录出现次数最多的字符
    var reArr; //返回最大值的位置数组
    for (var attr in obj) {
        var temp = obj[attr]['indexs'];
        if (temp.length > num) {
            num = temp.length;
            str = attr;
            reArr = temp;
        }
    }
    return {
        maxStr: str,
        indexs: reArr
    }
}

 