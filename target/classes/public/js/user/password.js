layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);



    /**
     * 监听表单的提交
     *     on监听 submit事件
     */
    form.on('submit(saveBtn)',function (data){
        console.log(data.field);

        //获取数据
        var oldPassword = $('[name="old_password"]').val();
        var newPassword = $('[name="new_password"]').val();
        var confirmPassword = $('[name="again_password"]').val();


        //新密码不能和原始密码一致
        if(newPassword == oldPassword){
            layer.msg("新密码不能和原始密码一致",{icon:5});
            return false;
        }

        //新密码要和确认密码一致
        if(newPassword != confirmPassword){
            layer.msg("新密码要和确认密码一致",{icon:5});
            return false;
        }

        //发送请求
        $.ajax({
            type:'post',
            url: ctx + "/user/update",
            data:{
                oldPassword:oldPassword,
                newPassword:newPassword,
                confirmPassword:confirmPassword
            },
            dataType:'json',
            success:function (data){
                if(data.code == 200){
                    //清楚登录状态，删cookie
                    $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                    $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                    $.removeCookie("trueName",{domain:"localhost",path:"/crm"});

                    //跳转登录页面
                    window.parent.location.href = ctx+"/index";
                }else{
                    layer.msg(data.msg,{icon:5});
                }
            }

        });


        return false;//阻止表单提交
    })


});

