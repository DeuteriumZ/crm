layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    formSelects = layui.formSelects;

    /**
     * 加载下拉框角色数据
     */
    formSelects.config('selectId',{
        type:"post",
        searchUrl:ctx + "/role/queryAllRoles?id="+$('[name="id"]').val(),
        //自定义返回数据中name的key, 默认 name
        keyName: 'rname',
        //自定义返回数据中value的key, 默认 value
        keyVal: 'id'
    },true);

    /**
     * 监听表单的提交
     */
    form.on("submit(RgisterUserUser)",function (data){
        // 提交数据时的加载层 （https://layer.layui.com/）
        var index = layer.msg("数据提交中,请稍后...",{
            icon:16, // 图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });

        var url = ctx+"/user/save";
        var id = $('[name="id"]').val();
        //如果进入判断则是修改
        if(id){
            url = ctx+"/user/Rgister";
        }

        //发送请求
        $.post(url,data.field,function (data){
            if(data.code == 200){
                //关闭弹出框
                layer.close(index);
                //关闭iframe层
                layer.closeAll("iframe");
                //刷新父页面，将添加的新数据展示
                window.parent.location.href = ctx+"/index";
            }else{
                layer.msg(data.msg,{icon:5})
            }
        });

        return false;
    });
});