//Client.js 
const OSS = require('ali-oss');

export default function Client(data) {
    //后台返回数据
    return new OSS({
        region: data.region,
        accessKeyId: data.accessKeyId,
        accessKeySecret: data.accessKeySecret,
        stsToken: data.security,
        bucket: data.bucket
    })
}
