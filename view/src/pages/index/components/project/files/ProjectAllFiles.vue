<template>
    <el-row style="height: 100%;display: flex;">
        <Table :data="files"
               :columns="columns"
               highlight-row
               @row-mouse-enter="handleRowMouseEnter"
               @row-mouse-leave="handleRowMouseLeave"
               style="flex: 1;border-top: 1px solid #DCDFE6;">
            <template slot-scope="scope" slot="fileName">
                <div style="display: flex;align-items: center;">
                    <div style="width: 24px;height: 24px;margin-right: 5px;line-height: 24px;text-align: center;">
                        <i class="file-item-type-icon icon css3-icon medium-blue"></i>
                    </div>
                    <div style="display: inline-block;flex: 1;">
                        <span>{{scope.row.fileName}}</span>
                        <el-tooltip effect="dark" :content="scope.row.starred ? '取消星标' : '添加星标'" placement="top">
                            <i :class="['file-item-icon', scope.row.starred ? 'el-icon-star-on' : 'el-icon-star-off']"></i>
                        </el-tooltip>
                    </div>
                    <i v-show="scope.row._show" class="file-item-icon el-icon-more"></i>
                    <i v-show="scope.row.shared || scope.row._show"
                       :class="['file-item-icon', 'el-icon-share', {'shared': scope.row.shared}]"></i>
                </div>
            </template>
            <template slot-scope="scope" slot="fileId">
                <span>{{scope.row.fileId ? scope.row.fileId : '-'}}</span>
            </template>
        </Table>
        <project-file-detail-side></project-file-detail-side>
    </el-row>
</template>

<script>

    import ProjectFileDetailSide from "./ProjectFileDetailSide";

    export default {
        name: 'project-all-files',
        components: {
            ProjectFileDetailSide
        },
        data() {
            return {
                fileDetailDrawerVisible: false,
                columns: [
                    {type: 'selection', width: 60, align: 'center'},
                    {title: '文件名', slot: 'fileName', minWidth: 240},
                    {title: '创建者', key: 'creator'},
                    {title: '最后更新', key: 'updateTime'},
                    {title: '文件大小', key: 'fileSize'},
                    {title: '文件 ID', slot: 'fileId'}
                ],
                files: [
                    {fileName: 'Cloud Studio.pdf', starred: true, shared: true, creator: '张三', updateTime: '123', fileSize: '10M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                ]
            }
        },
        mounted() {
            // const tableBody = this.$refs['filesTable'].$el.querySelector('.el-table__body');

        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
        },
        methods: {
            handleFilesRowCLick(row, column, e) {
                this.fileDetailDrawerVisible = true;
            },
            handleRowMouseEnter(row, e) {
                console.log(row);
                row._show = true;
            },
            handleRowMouseLeave(row, e) {
                console.log(row);
                delete row._show;
            }
        },
        watch: {
            projectName(val) {
                document.title = `全部文件-${val} - ${this.settings.title}`;
            }
        }
    }

</script>

<style>
    .file-item-type-icon:before {
        font-size: 16px;
    }
    .file-item-icon {
        text-align: center;
        font-size: 16px;
        display: inline-block;
        width: 24px;
        height: 24px;
        line-height: 24px;
        vertical-align: middle;
    }
    .file-item-icon:hover {
        cursor: pointer;
        background-color: #e2e4e6;
        border-radius: 5px;
    }
    .file-item-icon.el-icon-star-off:hover {
        background-color: transparent;
        border-radius: 0;
        color: #606266;
    }
    .file-item-icon.el-icon-star-on {
        font-size: 20px;
        color: #F8C540;
    }
    .file-item-icon.el-icon-star-on:hover {
        background-color: transparent;
        border-radius: 0;
        color: #F8C540;
    }

    .file-item-icon.el-icon-share.shared {
        color: #409EFF;
    }

    .ivu-table-row-highlight .el-icon-more,
    .ivu-table-row-highlight .el-icon-share,
    .ivu-table-row-hover .el-icon-more,
    .ivu-table-row-hover .el-icon-share {
        display: inline-block !important;
    }
</style>
