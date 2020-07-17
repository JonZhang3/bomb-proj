<template>
    <div
            class="at-table"
            :class="{
      'at-table--fixHeight': this.height,
      'at-table--stripe': this.stripe,
      [`at-table--${this.size}`]: this.size,
      [`at-table--border`]: this.border
    }"
            :style="tableStyles">

        <!-- S Content -->
        <div class="at-table__content" :style="contentStyle">
            <!-- S Header -->
            <div class="at-table__header" v-if="height">
                <table>
                    <colgroup>
                        <col v-for="(column, index) in columnsData" :width="setCellWidth(column, index)">
                    </colgroup>
                    <thead class="at-table__thead" ref="header">
                    <tr>
                        <!-- S Checkbox -->
                        <th v-if="optional" class="at-table__cell at-table__column-selection">
                            <el-checkbox :value="isSelectAll" @change="toggleSelect"></el-checkbox>
                        </th>
                        <!-- E Checkbox -->
                        <!-- S Column th -->
                        <th
                                v-for="(column, index) in columnsData"
                                v-if="column.type !== 'selection'"
                                class="at-table__cell at-table__column"
                                :class="column.className"
                                :style="{cursor: column.sortType ? 'pointer' : 'text'}"
                                @click="column.sortType && handleSort(index)">
                            {{ column.title }}
                            <template v-if="column.sortType">
                                <div class="caret-wrapper"
                                     :class="{
                                    'sort-asc': sortActiveIndex === index && column._sortType === 'asc',
                                    'sort-desc': sortActiveIndex === index && column._sortType === 'desc'
                                }">
                                    <span class="sort-caret ascending" @click.stop="handleSort(index, 'asc')"></span>
                                    <span class="sort-caret descending" @click.stop="handleSort(index, 'desc')"></span>
                                </div>
                            </template>
                        </th>
                        <!-- E Column th -->
                    </tr>
                    </thead>
                </table>
            </div>
            <!-- E Header -->

            <!-- S Body -->
            <div class="at-table__body" :style="bodyStyle">
                <table>
                    <colgroup>
                        <col v-for="(column, index) in columnsData" :width="setCellWidth(column, index)">
                    </colgroup>
                    <thead class="at-table__thead" v-if="!height" ref="header">
                    <tr>
                        <!-- S Checkbox -->
                        <th v-if="optional" class="at-table__cell at-table__column-selection">
                            <el-checkbox :value="isSelectAll" @change="toggleSelect"></el-checkbox>
                        </th>
                        <!-- E Checkbox -->
                        <!-- S Column th -->
                        <th
                            v-for="(column, index) in columnsData"
                            v-if="column.type !== 'selection'"
                            class="at-table__cell at-table__column"
                            :class="column.className"
                            :style="{cursor: column.sortType ? 'pointer' : 'text'}"
                            @click="column.sortType && handleSort(index)">
                            {{ column.title }}
                            <template v-if="column.sortType">
                                <div class="caret-wrapper"
                                     :class="{
                                    'sort-asc': sortActiveIndex === index && column._sortType === 'asc',
                                    'sort-desc': sortActiveIndex === index && column._sortType === 'desc'
                                }">
                                    <span class="sort-caret ascending" @click.stop="handleSort(index, 'asc')"></span>
                                    <span class="sort-caret descending" @click.stop="handleSort(index, 'desc')"></span>
                                </div>
                            </template>
                        </th>
                        <!-- E Column th -->
                    </tr>
                    </thead>

                    <tbody class="at-table__tbody" v-if="sortData.length" ref="body">
                    <template v-for="(item, index) in sortData">
                        <tr :class="{'at-table-row-active': rowActiveIndex === index}"
                            @click.stop="handleRowClick(index, item, $event)"
                            @mouseenter.stop="handleRowMouseenter(index, item, $event)"
                            @mouseleave.stop="handleRowMouseleave(index, item, $event)">
                            <td v-if="optional" class="at-table__cell at-table__column-selection">
                                <el-checkbox v-model="objData[index].isChecked" @change="changeRowSelection"></el-checkbox>
                            </td>
                            <td v-for="(column, cindex) in columns" v-if="column.type !== 'selection'" class="at-table__cell">
                                <template v-if="column.render">
                                    <Cell :item="item" :column="column" :index="index" :render="column.render">
                                        {{column.slot}}
                                    </Cell>
                                </template>
                                <template v-else>
                                    {{ item[column.key] }}
                                </template>
                            </td>
                        </tr>
                    </template>
                    </tbody>

                    <tbody class="at-table__tbody" v-else>
                    <tr>
                        <td class="at-table__cell at-table__cell--nodata"
                            :colspan="optional ? columns.length + 1 : columns.length">
                            <slot name="emptyText">{{ t('at.table.emptyText') }}</slot>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- E Body -->
        </div>
        <!-- E Content -->

        <!-- S Pagination -->
        <!--        <div v-if="pagination && total" class="at-table__footer" ref="footer">-->
        <!--            <el-pagination-->
        <!--                background-->
        <!--                layout="total, prev, pager, next"-->
        <!--                :current-page="currentPage"-->
        <!--                small-->
        <!--                :total="total"-->
        <!--                :page-size="pageSize"-->
        <!--                :show-sizer="showPageSizer"-->
        <!--                :show-quickjump="showPageQuickjump"-->
        <!--                @page-change="pageChange"-->
        <!--                @pagesize-change="pageSizeChange"></el-pagination>-->
        <!--        </div>-->
        <!-- E Pagination -->
    </div>
</template>

<script>

    import Locale from 'element-ui/lib/mixins/locale';
    import {getStyle, deepCopy} from '../../common/utils'
    import Cell from './render';
    import {addClass, removeClass} from '../../common/utils';

    export default {
        name: 'BombTable',
        components: {
            Cell
        },
        mixins: [Locale],
        props: {
            size: {
                type: String,
                default: 'normal'
            },
            stripe: {
                type: Boolean,
                default: false
            },
            border: {
                type: Boolean,
                default: false
            },
            data: {
                type: Array,
                default() {
                    return []
                }
            },
            columns: {
                type: Array,
                default() {
                    return []
                }
            },
            optional: {
                type: Boolean,
                default: false
            },
            pagination: {
                type: Boolean,
                default: false
            },
            pageSize: {
                type: Number,
                default: 10
            },
            showPageTotal: {
                type: Boolean,
                default: true
            },
            showPageSizer: {
                type: Boolean,
                default: false
            },
            showPageQuickjump: {
                type: Boolean,
                default: false
            },
            height: {
                type: [Number, String]
            },
            highlightCurrentRow: {
                type: Boolean,
                default: false
            },
            defaultHighlightRowIndex: {
                type: Number,
                default: -1
            }
        },
        data() {
            return {
                objData: this.makeObjData(), // use for checkbox to select all
                sortData: [], // use for sort or paginate
                allData: [],
                columnsData: this.makeColumns(),
                total: 0,
                bodyHeight: 0,
                pageCurSize: this.pageSize,
                columnsWidth: {},
                currentPage: 1,
                sortActiveIndex: 0,
                rowActiveIndex: this.defaultHighlightRowIndex
            }
        },
        watch: {
            height() {
                this.calculateBodyHeight()
            },
            allData() {
                this.total = this.allData.length
            },
            sortData() {
                this.handleResize()
            },
            pageCurSize() {
                this.sortData = this.makeDataWithPaginate()
            },
            data() {
                this.sortData = this.makeDataWithSortAndPage()
            }
        },
        computed: {
            tableStyles() {
                const styles = {}

                if (this.height) {
                    styles.height = `${this.height}px`
                }
                if (this.width) {
                    styles.width = `${this.width}px`
                }

                return styles
            },
            isSelectAll() {
                let isAll = true
                if (!this.sortData.length) {
                    isAll = false
                }
                for (let i = 0, len = this.sortData.length; i < len; i++) {
                    if (!this.objData[this.sortData[i].index].isChecked) {
                        isAll = false
                        break
                    }
                }

                return isAll
            },
            bodyStyle() {
                const styles = {}
                if (this.bodyHeight !== 0) {
                    const headerHeight = parseInt(getStyle(this.$refs.header, 'height')) || 0
                    styles.height = `${this.bodyHeight}px`
                    styles.marginTop = `${headerHeight}px`
                }
                return styles
            },
            contentStyle() {
                const styles = {}
                if (this.bodyHeight !== 0) {
                    const headerHeight = parseInt(getStyle(this.$refs.header, 'height')) || 0
                    styles.height = `${this.bodyHeight + headerHeight}px`
                }
                return styles
            }
        },
        methods: {
            calculateBodyHeight() {
                if (this.height) {
                    this.$nextTick(() => {
                        const headerHeight = parseInt(getStyle(this.$refs.header, 'height')) || 0
                        const footerHeight = parseInt(getStyle(this.$refs.footer, 'height')) || 0

                        this.bodyHeight = this.height - headerHeight - footerHeight
                    })
                } else {
                    this.bodyHeight = 0
                }
            },
            makeColumns() {
                const columns = deepCopy(this.columns)
                columns.forEach((column, idx) => {
                    column._index = idx
                    column._sortType = 'normal'

                    if (column.sortType) {
                        column._sortType = column.sortType
                        // column.sortType = column.sortType
                    }
                })
                return columns
            },
            makeData() {
                const data = deepCopy(this.data)
                data.forEach((row, idx) => {
                    row.index = idx
                })
                return data
            },
            makeObjData() {
                const rowData = {}

                this.data.forEach((row, index) => {
                    const newRow = deepCopy(row)

                    newRow.isChecked = !!newRow.isChecked

                    rowData[index] = newRow
                })

                return rowData
            },
            makeDataWithSortAndPage(pageNum) {
                let data = []
                let allData = []

                allData = this.makeDataWithSort()
                this.allData = allData

                data = this.makeDataWithPaginate(pageNum)
                return data
            },
            makeDataWithPaginate(page) {
                page = page || 1
                const pageStart = (page - 1) * this.pageCurSize
                const pageEnd = pageStart + this.pageCurSize
                let pageData = []

                if (this.pagination) {
                    pageData = this.allData.slice(pageStart, pageEnd)
                } else {
                    pageData = this.allData
                }
                return pageData
            },
            makeDataWithSort() {
                let data = this.makeData()
                let sortType = 'normal'
                let sortIndex = -1

                for (let i = 0, len = this.columnsData.length; i < len; i++) {
                    if (this.columnsData[i].sortType && this.columnsData[i].sortType !== 'normal') {
                        sortType = this.columnsData[i].sortType
                        sortIndex = i
                        break
                    }
                }

                if (sortType !== 'normal') {
                    data = this.sort(data, sortType, sortIndex)
                }

                return data
            },
            handleSelectAll() {
                const status = !this.isSelectAll

                for (const data of this.sortData) {
                    this.objData[data.index].isChecked = status
                }

                const selection = this.getSelection()

                status && this.$emit('on-select-all', selection)
                this.$emit('on-selection-change', selection)
            },
            handleSort(index, type) {
                this.sortActiveIndex = index;
                const key = this.columnsData[index].key
                const sortType = this.columnsData[index]._sortType
                const sortNameArr = ['normal', 'desc', 'asc']

                if (this.columnsData[index].sortType) {
                    if (!type) {
                        const tmpIdx = sortNameArr.indexOf(sortType)
                        if (tmpIdx >= 0) {
                            type = sortNameArr[(tmpIdx + 1) > 2 ? 0 : tmpIdx + 1]
                        }
                    }
                    if (type === 'normal') {
                        this.sortData = this.makeDataWithSortAndPage(this.currentPage)
                    } else {
                        this.sortData = this.sort(this.sortData, type, index)
                    }
                }
                this.columnsData[index]._sortType = type

                this.$emit('on-sort-change', {
                    column: JSON.parse(JSON.stringify(this.columns[this.columnsData[index]._index])),
                    order: type,
                    key
                })
            },
            sort(data, type, index) {
                const key = this.columnsData[index].key
                data.sort((a, b) => {
                    if (this.columnsData[index].sortMethod) {
                        return this.columnsData[index].sortMethod(a[key], b[key], type)
                    } else if (type === 'asc') {
                        return a[key] > b[key] ? 1 : -1
                    }
                    return a[key] < b[key] ? 1 : -1
                })
                return data
            },
            getSelection() {
                const selectionIndexArray = []
                for (const i in this.objData) {
                    if (this.objData[i].isChecked) {
                        selectionIndexArray.push(i | 0)
                    }
                }
                return JSON.parse(JSON.stringify(this.data.filter((data, index) => selectionIndexArray.indexOf(index) > -1)))
            },
            changeRowSelection() {
                const selection = this.getSelection()
                this.$emit('on-selection-change', selection)
            },
            pageChange(page) {
                this.$emit('on-page-change', page)
                this.currentPage = page
                this.sortData = this.makeDataWithPaginate(page)
            },
            pageSizeChange(size) {
                this.$emit('on-page-size-change', size)
                this.pageCurSize = size
            },
            handleResize() {
                this.$nextTick(() => {
                    const columnsWidth = {}

                    if (this.data.length) {
                        const $td = this.$refs.body.querySelectorAll('tr')[0].querySelectorAll('td')

                        for (let i = 0; i < $td.length; i++) {
                            const column = this.columnsData[i]
                            let width = parseInt(getStyle($td[i], 'width'))

                            if (column) {
                                if (column.width) {
                                    width = column.width
                                }
                                columnsWidth[column._index] = {width}
                            }
                        }
                    }

                    this.columnsWidth = columnsWidth
                })
            },
            setCellWidth(column, index) {
                let width = ''

                if (column.width) {
                    width = column.width
                } else if (this.columnsWidth[column._index]) {
                    width = this.columnsWidth[column._index].width
                }

                width = width === '0' ? '' : width
                return width
            },
            handleRowMouseenter(index, item, e) {
                item._isHover = true;
                addClass(e.target, 'at-table-row-hover');
            },
            handleRowMouseleave(index, item, e) {
                removeClass(e.target, 'at-table-row-hover');
            },
            handleRowClick(index, item, e) {
                this.rowActiveIndex = index;
                this.$emit('on-row-click', index, item, e);
            },
            toggleSelect(checked) {
                for (const data of this.sortData) {
                    this.objData[data.index].isChecked = checked;
                }

                const selection = this.getSelection();

                checked && this.$emit('on-select-all', selection);
                this.$emit('on-selection-change', selection);
            },
            selectAll(status) {
                this.toggleSelect(!!status);
            }
        },
        created() {
            this.sortData = this.makeDataWithSortAndPage()
        },
        mounted() {
            this.calculateBodyHeight();
            window.addEventListener('resize', this.handleResize)
        },
        beforeDestory() {
            window.removeEventListener('resize', this.handleResize)
        }
    }
</script>
