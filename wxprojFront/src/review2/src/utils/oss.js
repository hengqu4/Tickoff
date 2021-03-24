 
export default client
let OSS = require("ali-oss");

// 配置OSS参数
const client = new OSS({
    region: 'oss-cn-zhangjiakou',
    accessKeyId: 'LTAI4GE1zgH5iiZzP48o4euR',
    accessKeySecret: 't8Lf5hz8o0iF18azQXmGJDGc9PDx73',
    bucket: 'video-review-hb3'
  })
// 基本链接
const BASE_URL = "https://video-review-hb3.oss-cn-zhangjiakou.aliyuncs.com";

export function genRandomUrl(userId, fileType){
  let len = 10
  console.log('生成路径');
	var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz1234567890'
	var maxPos = chars.length
  let name = '';
	for (let i = 0; i < len; i++) {
		name += chars.charAt(Math.floor(Math.random() * maxPos))
  }
  let pre = '';
  if (fileType == 'mp4'){
    pre = 'video'
  }else if(fileType == 'jpg' || fileType == 'png'){
    pre = 'img'
  }else{
    return ;
  }
  let filepath = pre + '/' + userId + '/' + name + '.' + fileType;
  return filepath
}
/**
 *
 * @param file      文件对象
 * @param filepath  oss对象路径
 * @param updateProcess  上传进度回调函数
 * @returns url     文件url
 */
export async function uploadObject(file, filepath, updateProcess) {
  // console.log('开始上传'); 
  let hasUpload = 0;
  let result = await client.multipartUpload(filepath, file, {
    progress: function (p, checkpoint) {
      // 断点记录点。浏览器重启后无法直接继续上传，您需要手动触发上传操作。
      hasUpload += checkpoint.partSize;
      updateProcess(hasUpload/checkpoint.fileSize);
    }}
  );
  // console.log('完成上传')
  if (result.res.status == 200) {
    return  BASE_URL + '/' + filepath;
  }
}

export function genFullPath(filepath){
  return BASE_URL + '/' + filepath;
}


export async function deleteObject(filepath){
  if(filepath.indexOf(BASE_URL) != -1){
    filepath = filepath.substring(BASE_URL.length);
  }
  if(filepath.length<1){
    return;
  }
  console.log('删除: '+filepath)
  let result = await client.delete(filepath);
}

