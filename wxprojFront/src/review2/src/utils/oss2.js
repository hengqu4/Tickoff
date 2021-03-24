import co from 'co'
import OSS from 'ali-oss'

const OSSConfig = {
	uploadHost: 'http://remote-edu.oss-cn-beijing.aliyuncs.com', //OSS上传地址
	type: 'scs',
	ossParams: {
		region: 'oss-cn-beijing',
        success_action_status: '200', // 默认200
        accessKeyId: 'LTAI4GArwrhEGPkUvpy79Uwb',
        accessKeySecret: 'lBS04aIwoVh3uO3TGKGfzqd8wxzAcJ',
        bucket: 'remote-edu'
	},
}

function random_string(len) {
	len = len || 32
	var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'
	var maxPos = chars.length
	var pwd = ''
	for (let i = 0; i < len; i++) {
		pwd += chars.charAt(Math.floor(Math.random() * maxPos))
	}
	return pwd
}

/**
 *
 * @param event 上传图片的资源路径
 * @param type 图片资源类型
 * @param dir oss要保存的文件夹
 * @returns {OSS文件路径}
 */

function uploadOSS(event, type,dir = 'review/') {
    return new Promise((resolve, reject) => {
        var client = new OSS({
            region: OSSConfig.ossParams.region,
            accessKeyId: OSSConfig.ossParams.accessKeyId,
            accessKeySecret: OSSConfig.ossParams.accessKeySecret,
            bucket: OSSConfig.ossParams.bucket,
        });
        var file;
        if (type == 0) {
            file = event;
        } else {
            file = event.target.files[0];
        }
        let randomName = `${dir}${random_string(6)}_${file.name}`;
        co(function* () {
            var result = yield client.multipartUpload(randomName, file);
            console.log(`${OSSConfig.uploadHost}/${result.name}`);
            resolve(`${OSSConfig.uploadHost}/${result.name}`);
        }).catch(function (err) {
            console.log(err);
            reject(err);
        });
    });
}

export { uploadOSS }
