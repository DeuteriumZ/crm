layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var treeTable = layui.treetable;

    // 渲染表格
    treeTable.render({
        treeColIndex: 1,
        treeSpid: -1,
        treeIdName: 'id',
        treePidName: 'parentId',
        elem: '#munu-table',
        url: ctx+'/module/list',
        toolbar: "#toolbarDemo",
        treeDefaultClose:true,
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'moduleName', minWidth: 100, title: '菜单名称'},
            {field: 'optValue', title: '权限码'},
            {field: 'url', title: '菜单url'},
            {field: 'createDate', title: '创建时间'},
            {field: 'updateDate', title: '更新时间'},
            {
                field: 'grade', width: 80, align: 'center', templet: function (d) {
                    if (d.grade == 0) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }
                    if(d.grade==1){
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                    if (d.grade == 2) {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', width: 260, align: 'center', title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });


    /**
     * 监听头部工具栏
     */
    table.on("toolbar(munu-table)",function (data) {
        if(data.event == "expand"){
            //全部展开
            treeTable.expandAll('#munu-table');
        }else if(data.event == "fold"){
            //全部折叠
            treeTable.foldAll('#munu-table');
        }else if(data.event == "add"){
            openAddModuleDialog(0,-1);
        }
    });


    /**
     * 监听行部工具栏
     */
    table.on("tool(munu-table)",function (data) {
        if(data.event == "add"){
            if(data.data.grade == 2){
                layer.msg("未设置四级模块")
                return;
            }
            //打开添加窗口
            openAddModuleDialog(data.data.grade+1,data.data.id);
        }else if(data.event == "edit"){
            openupdateModuleDialog(data.data.id);

        }else if(data.event == "del"){
            console.log(data);

            layer.confirm('确定删除当前资源？', {icon: 3, title: "资源管理"}, function (index) {
                $.post(ctx+"/module/delete",{mId:data.data.id},function (data) {
                    if(data.code==200){
                        layer.msg("操作成功！",{icon:6});
                        parent.location.reload();  刷新页面
                        // data.del();
                    }else{
                        layer.msg(data.msg, {icon: 5});
                    }
                });
            })
        }
    });
});


//修改窗口
function openupdateModuleDialog(id){
    var url  =  ctx+"/module/updateModulePage?id="+id;
    var title="菜单更新";
    layui.layer.open({
        title : title,
        type : 2,
        area:["700px","450px"],
        maxmin:true,
        content : url
    });
}


//打开添加窗口
function openAddModuleDialog(grade,parentId){
    var title = "菜单管理-添加模块";
    var url = ctx + "/module/toAdd?grade="+grade+"&parentId="+parentId;
    layer.open({
        title:title,
        type:2,
        content:url,
        maxmin:true,
        area:["700px","450px"]
    });
}