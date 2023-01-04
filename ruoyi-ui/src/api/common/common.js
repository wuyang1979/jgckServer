export function intCovString(val) {
  var type = typeof (val);
  if ('string' === type) {
    return val;
  } else {
    var str;
    str = JSON.stringify(val)
    return str;
  }
}


export function arrCovString(val) {
  if (Array.isArray(val)) {
    var str = "";
    for (var i = 0; i < val.length; i++) {
      str += val[i] + ","
    }
    return str.substring(str.length - 1, str)
  } else {
    return val
  }
}


export function getNowDateTime() {
  var date = new Date();
  var sign2 = ":";
  var year = date.getFullYear() // 年
  var month = date.getMonth() + 1; // 月
  var day = date.getDate(); // 日
  var hour = date.getHours(); // 时
  var minutes = date.getMinutes(); // 分
  var seconds = date.getSeconds() //秒
  // 给一位数的数据前面加 “0”
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (day >= 0 && day <= 9) {
    day = "0" + day;
  }
  if (hour >= 0 && hour <= 9) {
    hour = "0" + hour;
  }
  if (minutes >= 0 && minutes <= 9) {
    minutes = "0" + minutes;
  }
  if (seconds >= 0 && seconds <= 9) {
    seconds = "0" + seconds;
  }
  return year + "-" + month + "-" + day + " " + hour + sign2 + minutes + sign2 + seconds;
}

