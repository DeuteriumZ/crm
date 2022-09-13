layui.use(['form','jquery','jquery_cookie','element', 'layer', 'layuimini','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
   
        var $ = layui.jquery,
            layer = layui.layer,
            $ = layui.jquery_cookie($);    
    form.on("submit(register)",function (){


        window.location.href = ctx + "/user/add_Rgister";
    });


    /**
     * 监听表单的提交
     *     on监听 submit事件
     */
    form.on("submit(login)",function (data){
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}

        //数据校验 TODO
        //发送请求
        $.ajax({
            type:"post",
            url: ctx + "/user/login",
            data:{
                userName:data.field.username,
                userPwd:data.field.password
            },
            dataType:'json',
            success:function (data){
                if(data.code == 200){

                    //存储cookie
                    $.cookie("userIdStr",data.result.userId);
                    $.cookie("userName",data.result.userName);
                    $.cookie("trueName",data.result.trueName);

                    //记住密码
                    if($("#rememberMe").prop("checked")){
                        $.cookie("userIdStr", data.result.userId, { expires: 7 });
                        $.cookie("userName", data.result.userName, { expires: 7 });
                        $.cookie("trueName", data.result.trueName, { expires: 7 });
                    }

                    //跳转到首页
                    window.location.href = ctx + "/main";
                }else{
                    layer.msg(data.msg,{icon:5});
                }
            }
        });
        

        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });


});

/*
function istrue(str){
    if(str == null || str.trim() == ""){
        return true;
    }
    return false;
}*/
