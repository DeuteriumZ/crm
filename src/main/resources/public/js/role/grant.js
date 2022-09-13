$(function () {
    loadModuleInfo(); 
});

var zTreeObj;
//加载权限树形结构
function loadModuleInfo(){
    $.ajax({
        type:'get',
        url:ctx+'/module/queryAllModules?roleId='+$('[name="roleId"]').val(),
        dataType:'json',
        success:function (data){

            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                check: {
                    enable: true  //复选框
                },
                data: {
                    simpleData: {
                        enable: true  //支持简单json数据的展示
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck //用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
                }
            };

            $(document).ready(function(){
                zTreeObj = $.fn.zTree.init($("#test1"), setting, data);
            });
        }
    });
}

//监听多选框单选框的选中状态
function zTreeOnCheck(){
    var nodes = zTreeObj.getCheckedNodes(true); //获取所有被选中的节点数据
    //循环拼接mIds
    var str = "mIds="
    for(var i = 0; i < nodes.length; i++){
        //判断是否是倒数第二个
        if(i < nodes.length - 1){
            str += nodes[i].id + "&mIds=";
        }else{
            str += nodes[i].id;
        }
    }
    console.log(str);

    //发送请求
    $.ajax({
        type:'post',
        url:ctx+"/role/addGrant?roleId="+$('[name="roleId"]').val(),
        data:str,             //模块id
        dataType: 'json',
        success:function (data){
            if(data.code == 200){

            }else{
              // layer.msg(data.msg,{icon:5})
            }
        }
    });
}




