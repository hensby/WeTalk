package wetalk.software.bupt.com.wetalk.listener;

import wetalk.software.bupt.com.wetalk.model.po.WeTalkMsg;

/**
 * Created by zhangjie on 2017/12/6.
 */
public abstract interface UploadListener {

    /** 上传开始前，用于先将图片显示出来，然后再上传成功之后再将msg发送出去
     * @Title: onStart
     * @Description: TODO
     * @param  msg :其中的content为拍照或者选择图库中得到的本地图片的地址
     * @return
     * @throws
     */
    public abstract void onStart(WeTalkMsg msg);

    /** 上传成功
     * @Title: onSuccess
     * @Description: TODO
     * @param  path
     * @return
     * @throws
     */
    public abstract void onSuccess();


    /** 上传失败
     * @Title: onFailure
     * @Description: TODO
     * @param  arg0
     * @param  arg1
     * @return
     * @throws
     */
    public abstract void onFailure(int error, String arg1);


}
