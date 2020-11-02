package com.qyai.beaconlib.html;


import com.lib.common.BaseMvp.presenter.ReqPresenter;

public class HtmlPersener extends ReqPresenter<HtmlView> {

    HtmlModel htmlModel;
    private final static int REQ_ADD = 10;
    private final static int REQ_DELECT = 20;
    private final static int REQ_UPDATE = 30;
    private final static int REQ_QUERY = 40;

    public HtmlPersener() {
        htmlModel = new HtmlModel(this);
    }

    public void setReqAdd(Object para){
        htmlModel.addBlueToothInfo(REQ_ADD,para);
    }
    public void setReqDelect(Object para){
        htmlModel.addBlueToothInfo(REQ_DELECT,para);
    }
    public void setReqUpdate(Object para){
        htmlModel.addBlueToothInfo(REQ_UPDATE,para);
    }
    public void setReqQuery(Object para){
        htmlModel.addBlueToothInfo(REQ_QUERY,para);
    }

    @Override
    public void reqSuccessful(int id, Object baseResponse) {
        switch (id) {
            case REQ_ADD:
                break;
            case REQ_DELECT:
                break;
            case REQ_UPDATE:
                break;
            case REQ_QUERY:
                break;

        }
    }

    @Override
    public void reqErro(int id, Object baseResponse, String err) {
        switch (id) {
            case REQ_ADD:
                break;
            case REQ_DELECT:
                break;
            case REQ_UPDATE:
                break;
            case REQ_QUERY:
                break;

        }
    }
}
