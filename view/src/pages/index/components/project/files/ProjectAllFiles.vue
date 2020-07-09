<template>
    <el-row style="height: 100%;display: flex;flex-direction: column;">
        <el-row>A</el-row>
        <el-row style="display: flex;flex: 1;overflow: hidden;">
            <el-row ref="tableWrapper" style="display: flex;flex-direction: column; flex: 1;border-top: 1px solid #DCDFE6;position: relative;">
                <Table :data="files"
                       :columns="columns"
                       :height="fileTableHeight"
                       ref="fileTable" style="flex: 1;"
                       @on-selection-change="handleTableSelectChange"
                       highlight-row>
                    <template slot-scope="scope" slot="fileName">
                        <file-name-cell :data="scope.row"></file-name-cell>
                    </template>
                    <template slot-scope="scope" slot="fileId">
                        <span>{{scope.row.fileId ? scope.row.fileId : '-'}}</span>
                    </template>
                </Table>
                <div ref="tableFooter"
                     @transitionend="resizeTableHeight" data-root="root"
                     :class="['project-files-footer-wrapper', {'is-show': customOperationFooterShow }]">
                    <div class="project-files-footer-wrapper-inner">
                        <div>
                            <i class="el-icon-s-unfold" style="font-size: 18px;color: #409EFF;margin-right: 5px;"></i>
                            <span style="font-size: 14px;">已选 {{selection.length}} 项</span>
                            <el-divider direction="vertical"></el-divider>
                            <el-button icon="el-icon-download" size="mini">打包下载</el-button>
                            <el-button size="mini">移动到...</el-button>
                            <el-button size="mini">复制到...</el-button>
                            <el-button type="danger" size="mini">删除</el-button>
                        </div>
                        <el-button type="primary" icon="el-icon-s-operation" size="mini"
                                   style="flex-shrink: 0" @click="$refs['fileTable'].selectAll(false)">取消操作</el-button>
                    </div>
                </div>
            </el-row>
            <project-file-detail-side></project-file-detail-side>
        </el-row>
    </el-row>
</template>

<script>

    import ProjectFileDetailSide from "./ProjectFileDetailSide";
    import FileNameCell from "./FileNameCell";

    export default {
        name: 'project-all-files',
        components: {
            ProjectFileDetailSide,
            FileNameCell
        },
        data() {
            return {
                customOperationFooterShow: false,
                fileTableHeight: 0,
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
                    {fileName: "Cloud Studio.pdf", creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
                ],
                selection: []
            }
        },
        mounted() {
            this.fileTableHeight =
                this.$refs['tableWrapper'].$el.offsetHeight - this.$refs['tableFooter'].offsetHeight;
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
        },
        methods: {
            handleFilesRowCLick(row, column, e) {

            },
            handleTableSelectChange(selection) {
                this.selection = selection;
                this.customOperationFooterShow = selection.length > 0;
            },
            resizeTableHeight(e) {
                if(e.target.dataset.root === 'root') {
                    this.fileTableHeight =
                        this.$refs['tableWrapper'].$el.offsetHeight - this.$refs['tableFooter'].offsetHeight;
                }
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
    .project-files-footer-wrapper {
        height: 0;
        background-color: #ffffff;
        width: 100%;
        transition: height .3s linear;
    }
    .project-files-footer-wrapper-inner {
        opacity: 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        color: #303133;
        padding: 8px 25px;
        transition: opacity .3s linear;
    }
    .project-files-footer-wrapper.is-show {
        height: 44px;
    }
    .project-files-footer-wrapper.is-show .project-files-footer-wrapper-inner {
        opacity: 1;
    }
</style>
