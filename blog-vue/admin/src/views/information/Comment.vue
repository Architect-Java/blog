<template>
  <div class="comment-container">
    <el-card shadow="never">
      <div class="comment-header">
        <el-form :inline="true">
          <el-form-item>
            <span class="title-label">用户昵称</span>
            <el-input
              size="small"
              v-model="nickName"
              placeholder="请输入用户昵称"
              style="width: 300px"
            ></el-input>
          </el-form-item>

          <el-form-item style="margin-left: 25px">
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索
            </el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">
              重置
            </el-button>
          </el-form-item>
        </el-form>

        <div class="button-container">
          <div class="left">
            <span class="span-label-title" style="margin-right: 20px">
              状态
            </span>
            <span
              @click="changeActive(-1)"
              :class="isAllow == -1 ? 'span-label-active' : 'span-label'"
            >
              全部
            </span>
            <span
              @click="changeActive(1)"
              :class="isAllow == 1 ? 'span-label-active' : 'span-label'"
            >
              已通过
            </span>
            <span
              @click="changeActive(0)"
              :class="isAllow == 0 ? 'span-label-active' : 'span-label'"
            >
              审核中
            </span>
          </div>
          <div class="right">
            <el-button
              type="success"
              plain
              icon="el-icon-circle-check"
              size="mini"
              @click="handlePassBatch"
            >
              批量通过
            </el-button>
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              @click="handleDeleteBatch"
            >
              批量删除
            </el-button>
          </div>
        </div>
      </div>

      <div class="comment-body">
        <el-table
          :data="tableData"
          border
          :header-cell-style="{ background: '#f8f8f9', color: '#606266' }"
          @selection-change="handleSelectionChange"
          ref="table"
          row-key="id"
          style="width: 100%"
          size="medium"
        >
          <el-table-column type="selection" width="50" align="center">
          </el-table-column>
          <el-table-column label="评论用户" align="center">
            <template slot-scope="scope">
              <span>
                {{ scope.row.nickName }}
              </span>
              <el-tag
                size="mini"
                style="margin-left: 5px"
                v-if="scope.row.identity == 0"
              >
                博主
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="回复人" align="center">
            <template slot-scope="scope">
              <span v-if="!scope.row.replyName"> 无 </span>
              <span v-else>
                {{ scope.row.replyName }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="用户头像" align="center" width="100">
            <template slot-scope="scope">
              <el-empty
                v-if="!commonAvatar"
                description="暂无图片"
                :image-size="40"
                style="height: 60px"
              ></el-empty>
              <el-image
                v-else-if="scope.row.identity == 0"
                :src="avatar"
              ></el-image>
              <el-image v-else :src="commonAvatar"></el-image>
            </template>
          </el-table-column>
          <el-table-column
            prop="content"
            label="评论内容"
            align="center"
            width="400"
          >
          </el-table-column>
          <el-table-column prop="ipSource" label="IP地址" align="center">
          </el-table-column>
          <el-table-column prop="ipAddress" label="IP属地" align="center">
          </el-table-column>
          <el-table-column label="评论时间" align="center">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 5px">
                {{ scope.row.commentTime | dateFilter }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="评论状态" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.allow == 1">已通过</el-tag>
              <el-tag v-else type="warning">审核中</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button
                v-if="isAllow == 0"
                type="text"
                @click="handlePass(scope.row)"
                icon="el-icon-circle-check"
              >
                通过
              </el-button>
              <el-button
                v-else
                type="text"
                @click="handleDelete(scope.row)"
                icon="el-icon-delete"
              >
                删除
              </el-button>
              <el-button
                type="text"
                @click="handleReply(scope.row)"
                icon="el-icon-circle-check"
              >
                回复
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="comment-footer">
        <el-pagination
          :page-sizes="[10, 20, 50, 100]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        >
        </el-pagination>
      </div>

      <el-dialog
        :visible="showReply"
        title="回复评论"
        width="600px"
        :before-close="closeReplay"
      >
        <el-form>
          <el-form-item label="回复内容">
            <el-input
              type="textarea"
              rows="10"
              v-model="commentVO.content"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleSubmit">确定</el-button>
          <el-button @click="closeReplay">取消</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import {
  getCommentList,
  deleteCommentById,
  deleteCommentBatchIds,
  passCommentById,
  passCommentBatchIds,
  getAllowCommentList,
  addComment,
} from "@/api/comment.js";

export default {
  name: "Comment",

  data() {
    return {
      // 当前用户头像
      avatar: this.$store.getters.userInfo.avatar,
      // 游客头像
      commonAvatar: this.$store.getters.avatar,
      // 用户昵称
      nickName: "",
      // 是否通过
      isAllow: -1,
      // 标签列表
      tableData: [],
      // 当前页数
      curPage: 1,
      // 页面大小
      size: 10,
      // 数据总条数
      total: 0,
      // 表格多选框
      multipleSelection: [],
      // 是否显示回复内容
      showReply: false,
      // 回复评论对象
      commentVO: {
        // 父ID
        parentId: undefined,
        // 评论内容
        content: "",
        // 用户昵称
        nickName: this.$store.getters.userInfo.nickName,
        // 用户身份
        identity: 0,
        // 评论文章
        articleId: undefined,
        // 回复人
        replyName: "",
      },
    };
  },

  mounted() {
    this.getTableData();
  },
  watch: {
    isAllow() {
      // 重新定义当前页数
      this.curPage = 1;
      // 获取表格数据（分页）
      this.getTableData();
    },
  },
  methods: {
    // 获取表格数据
    getTableData() {
      if (this.isAllow == -1) {
        // 获取表格数据（分页）
        getCommentList({
          curPage: this.curPage,
          size: this.size,
          nickName: this.nickName,
          allow: this.isAllow,
        }).then((result) => {
          this.tableData = result.list;
          this.total = result.total;
        });
      } else {
        // 根据分页状态获取所有评论（分页）
        getAllowCommentList({
          curPage: this.curPage,
          size: this.size,
          nickName: this.nickName,
          allow: this.isAllow,
        }).then((result) => {
          this.tableData = result.list;
          this.total = result.total;
        });
      }
    },
    // 更换查询状态
    changeActive(val) {
      this.isAllow = val;
    },
    // 点击查询事件
    handleQuery() {
      this.getTableData();
    },
    // 点击重置操作
    resetQuery() {
      // 清空用户昵称
      this.nickName = "";
      this.isAllow = -1;
      this.getTableData();
    },
    handleDeleteBatch() {},
    // 当页面大小发生变化时
    handleSizeChange(val) {
      this.size = val;
      // 重新获取数据
      this.getTableData();
    },
    // 当前页发生变化时
    handleCurrentChange(val) {
      this.curPage = val;
      // 重新获取数据
      this.getTableData();
    },
    // 点击批量删除数据
    handleDeleteBatch() {
      // 将选中的id进行存储
      const ids = [];
      this.multipleSelection.forEach((item) => {
        ids.push(item.id);
      });
      this.$confirm("确定删除这些评论吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 执行删除操作
          deleteCommentBatchIds(ids).then(() => {
            // 重新获取表格数据
            this.getTableData();
            // 清空表格多选框
            this.multipleSelection = [];
          });
        })
        .catch(() => {});
    },
    // 点击删除按钮
    handleDelete(row) {
      this.$confirm("确定删除这条评论吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 执行删除操作
          deleteCommentById(row.id).then(() => {
            // 刷新表格数据
            this.getTableData();
          });
        })
        .catch(() => {});
    },
    // 点击通过按钮
    handlePass(row) {
      this.$confirm("确定通过这条评论吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "success",
      })
        .then(() => {
          // 执行通过操作
          passCommentById(row.id).then(() => {
            // 刷新表格数据
            this.getTableData();
          });
        })
        .catch(() => {});
    },
    // 点击批量通过按钮
    handlePassBatch() {
      // 将选中的id进行存储
      const ids = [];
      this.multipleSelection.forEach((item) => {
        ids.push(item.id);
      });
      this.$confirm("确定通过这些评论吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "success",
      })
        .then(() => {
          // 执行通过操作
          passCommentBatchIds(ids).then(() => {
            // 刷新表格数据
            this.getTableData();
            console.log(this.multipleSelection);
            // 清空表格多选框
            this.multipleSelection = [];
            console.log(this.multipleSelection);
          });
        })
        .catch(() => {});
    },
    // 多选框按钮
    handleSelectionChange(val) {
      this.multipleSelection = val; //存储选中的数据
    },
    // 回复按钮响应时间
    handleReply(val) {
      this.showReply = true;
      // 判断当前评论的父ID
      if (val.parentId == 0) {
        // 如果当前评论的父Id为0，则将当前评论的ID作为回复的父ID
        this.commentVO.parentId = val.id;
      } else {
        this.commentVO.parentId = val.parentId;
      }
      // 定义回复文章ID
      this.commentVO.articleId = val.articleId;
      // 定义回复人姓名
      this.commentVO.replyName = val.nickName;
    },
    // 关闭回复评论
    closeReplay() {
      this.showReply = false;
      this.commentVO = {
        // 父ID
        parentId: undefined,
        // 评论内容
        content: "",
        // 用户昵称
        nickName: this.$store.getters.userInfo.nickName,
        // 用户身份
        identity: 0,
        // 评论文章
        articleId: undefined,
        // 回复人
        replyName: "",
      };
    },
    // 点击确定回复评论按钮
    handleSubmit() {
      addComment(this.commentVO).then((result) => {
        // 刷新评论
        this.getTableData();
        // 关闭对话框
        this.closeReplay();
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.comment-container {
  margin-top: 10px;
  .comment-header {
    margin-top: 10px;
    padding-left: 15px;
    .title-label {
      font-size: 14px;
      font-weight: 700;
      color: #606266;
      margin-right: 12px;
    }
    .button-container {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-right: 15px;
    }
    .span-label {
      cursor: pointer;
      font-size: 14px;
      font-weight: 600;
      color: #909399;
      margin-right: 20px;
    }
    .span-label-title {
      font-size: 14px;
      font-weight: 600;
      color: #909399;
      margin-right: 20px;
    }
    .span-label-active {
      cursor: pointer;
      font-size: 14px;
      font-weight: 600;
      color: #303133;
      margin-right: 20px;
    }
  }
  .comment-body {
    margin-top: 20px;
    padding-left: 15px;
    padding-right: 15px;
  }
  .comment-footer {
    padding-left: 15px;
    margin-top: 40px;
  }
}
</style>
