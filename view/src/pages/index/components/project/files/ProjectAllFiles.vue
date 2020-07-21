<template>
    <el-row style="height: 100%;display: flex;flex-direction: column;">
        <div
            style="display: flex;justify-content: space-between;padding: 10px 20px;border-top: 1px solid #DCDFE6;align-items: center;">
            <div style="display: flex;align-items: center;">
                <span style="display: inline-block; font-size: 18px;color: #303133;">全部文件</span>
                <el-divider direction="vertical"></el-divider>
                <el-input placeholder="搜索文件" size="small" style="flex: 1;">
                    <el-select v-model="searchType" slot="prepend" placeholder="请选择" style="width: 75px;">
                        <el-option label="全部" value="1"></el-option>
                        <el-option label="当前" value="2"></el-option>
                    </el-select>
                </el-input>
                <el-tooltip effect="dark" placement="top" content="”当前“指在当前文件夹搜索，不包含子文件夹">
                    <i class="el-icon-warning" style="color: #909399;margin-left: 5px;cursor: pointer;"></i>
                </el-tooltip>
            </div>
            <div style="display: flex;align-items: center;">
                <el-popover trigger="click" :visible-arrow="false" placement="bottom" >
                    <ul>
                        <li>文件夹</li>
                    </ul>
                    <el-button size="small"
                               slot="reference"
                               icon="el-icon-plus">新建<i class="el-icon-arrow-down el-icon--right"></i></el-button>
                </el-popover>
                <el-popover trigger="click" :visible-arrow="false" placement="bottom" >
                    <ul>
                        <li>上传文件</li>
                        <li>上传文件夹</li>
                    </ul>
                    <el-button size="small" style="margin-left: 10px;"
                               slot="reference" type="primary"
                               icon="el-icon-upload2">上传<i class="el-icon-arrow-down el-icon--right"></i></el-button>
                </el-popover>
                <span @click="handleSideTriggerClick" style="margin-left: 10px"
                      :class="['side-trigger', {'is-active': fileDetailSideBarShow}]"></span>
            </div>
        </div>
        <el-row ref="tableRoot" style="display: flex;flex: 1;overflow: hidden;">
            <el-row ref="tableWrapper" style="display: flex;flex-direction: column; flex: 1;position: relative;">
                <bomb-table :columns="columns" :data="files"
                            optional ref="fileTable" fix-header
                            :height="fileTableHeight" style="flex: 1;"
                            @on-selection-change="handleTableSelectChange"
                            @on-sort-change="handleTableSortChange"
                            @on-row-click="handleFilesRowCLick">
                    <template slot-scope="scope" slot="fileName">
                        <file-name-cell :data="scope"></file-name-cell>
                    </template>
                </bomb-table>
                <file-table-footer-operation :show="customOperationFooterShow"
                                             ref="tableFooter"
                                             @on-transition-end="resizeTableHeight"
                                             :selection="selection"
                                             :table-ref="$refs['fileTable']"></file-table-footer-operation>
            </el-row>
            <project-file-detail-side :data="activeRowData" ref="detailSide"
                                      :show="fileDetailSideBarShow"></project-file-detail-side>
        </el-row>
    </el-row>
</template>

<script>

    import ProjectFileDetailSide from "./ProjectFileDetailSide";
    import FileNameCell from "./FileNameCell";
    import FileTableFooterOperation from "./FileTableFooterOperation";

    export default {
        name: 'project-all-files',
        components: {
            ProjectFileDetailSide,
            FileNameCell,
            FileTableFooterOperation
        },
        data() {
            return {
                customOperationFooterShow: false,
                fileTableHeight: 0,
                searchType: '1',
                fileDetailSideBarShow: true,
                columns: [
                    {type: 'selection', width: '60', align: 'center'},
                    {title: '文件名', slot: 'fileName', key: 'fileName', sortType: 'normal'},
                    {title: '创建者', key: 'creator', sortType: 'normal'},
                    {title: '最后更新', key: 'updateTime', sortType: 'normal'},
                    {title: '文件大小', key: 'fileSize', sortType: 'normal'}
                ],
                files: [
                    {
                        fileName: '1Cloud Studio.pdf',
                        starred: true,
                        shared: true,
                        creator: '张三',
                        updateTime: '123',
                        fileSize: '10M'
                    },
                    {fileName: "2Cloud Studio.pdf", creator: '张三', updateTime: '123', fileSize: '12M'},
                    {fileName: '3Cloud Studio.pdf', creator: '张三', updateTime: '123', fileSize: '12M'},
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
                    {fileName: 'Cloud Studio.pdf234', creator: '张三', updateTime: '123', fileSize: '12M'},
                ],
                selection: [],// 被选中行的数组
                activeRowData: {}
            }
        },
        mounted() {
            this.fileTableHeight = this.$refs['tableWrapper'].$el.offsetHeight - this.$refs['tableFooter'].$el.offsetHeight;
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
        },
        methods: {
            handleFilesRowCLick(index, row, e) {
                this.activeRowData = row;
            },
            handleSideTriggerClick() {
                this.fileDetailSideBarShow = !this.fileDetailSideBarShow;
            },
            handleTableSelectChange(selection) {
                this.selection = selection;
                this.customOperationFooterShow = selection.length > 0;
            },
            resizeTableHeight() {
                this.fileTableHeight =
                    this.$refs['tableWrapper'].$el.offsetHeight - this.$refs['tableFooter'].$el.offsetHeight;
            },
            handleTableSortChange(column, order, key) {

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

</style>
