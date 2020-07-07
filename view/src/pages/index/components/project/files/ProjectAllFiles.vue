<template>
    <el-row style="height: 100%;display: flex;">
        <el-row style="flex: 1;border-top: 1px solid #DCDFE6;position: relative;">
            <Table :data="files"
                   :columns="columns"
                   highlight-row>
                <template slot-scope="scope" slot="fileName">
                    <file-name-cell :data="scope.row"></file-name-cell>
                </template>
                <template slot-scope="scope" slot="fileId">
                    <span>{{scope.row.fileId ? scope.row.fileId : '-'}}</span>
                </template>
            </Table>
            <div style="height: 44px;background-color: #ffffff;position: absolute;bottom: 0;width: 100%;padding: 10px">
                <span>已选 2 项</span>
            </div>
        </el-row>
        <project-file-detail-side></project-file-detail-side>
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
            }
        },
        watch: {
            projectName(val) {
                document.title = `全部文件-${val} - ${this.settings.title}`;
            }
        }
    }

</script>
