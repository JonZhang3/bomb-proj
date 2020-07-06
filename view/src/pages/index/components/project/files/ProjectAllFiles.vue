<template>
    <el-row style="height: 100%;display: flex;">
        <el-table :data="files"
                  style="flex: 1;border-top: 1px solid #DCDFE6;"
                  size="medium"
                  row-class-name="project-file-item"
                  highlight-current-row
                  ref="filesTable"
                  @cell-mouse-enter="handleFilesCellMouseEnter"
                  @row-click="handleFilesRowCLick">
            <el-table-column type="selection" width="50"></el-table-column>
            <el-table-column sortable label="文件名" min-width="240">
                <template slot-scope="scope">
                    <div style="display: flex;align-items: center;">
                        <div style="width: 24px;height: 24px;margin-right: 5px;line-height: 24px;text-align: center;">
                            <i class="file-item-type-icon icon css3-icon medium-blue"></i>
                        </div>
                        <div style="display: inline-block;flex: 1;">
                            <span>{{scope.row.fileName}}</span>
                            <i class="file-item-icon el-icon-star-off"></i>
                        </div>
                        <i v-show="false" class="file-item-icon el-icon-more"></i>
                        <i v-show="false" class="file-item-icon el-icon-share"></i>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="creator" sortable label="创建者"></el-table-column>
            <el-table-column prop="updateTime" sortable label="最后更新"></el-table-column>
            <el-table-column prop="fileSize" sortable label="文件大小"></el-table-column>
            <el-table-column prop="fileId" label="文件 ID">
                <template slot-scope="scope">
                    <span>{{scope.row.fileId ? scope.row.fileId : '-'}}</span>
                </template>
            </el-table-column>
        </el-table>
        <project-file-detail-side></project-file-detail-side>
        <el-drawer title="标题"
                   :append-to-body="true"
                   :visible.sync="fileDetailDrawerVisible" direction="rtl">
            <span>文件详细</span>
        </el-drawer>
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
                files: [
                    {fileName: 'Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '10M'},
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
            handleFilesCellMouseEnter(row, column, cell, e) {
                console.log(e.target.parentElement);
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
    }
    .file-item-icon:hover {
        cursor: pointer;
        background-color: #e2e4e6;
        border-radius: 5px;
    }
</style>
