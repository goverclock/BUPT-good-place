<template>
    <div class="header-cont">
        <div class="left">
            <h1>
                <router-link to="/">BUPT好去处</router-link>
            </h1>
        </div>
        <div class="right flex-center">
            <template v-if="isLogin">
                <div class="gap">
                    <router-link to="/personal/message"><el-icon>
                            <message />
                        </el-icon></router-link>
                </div>
                <el-dropdown trigger="click">
                    <div class="flex-center cursor">
                        {{ username }}<el-icon><caret-bottom /></el-icon>
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>个人中心</el-dropdown-item>
                            <el-dropdown-item>退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </template>
            <template v-else-if="$route.name !== 'Login'">
                <router-link to="/login">{{ ('登录') }}</router-link>
            </template>
        </div>
    </div>
</template>

<script setup>
const isLogin = ref(false)
const username = ref('admin')
isLogin.value = true;
</script>

<style lang="scss">
.header-cont {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    height: 100%;

    a {
        color: inherit;
        text-decoration: none;
    }

    h1 {
        margin: 0;
        font-size: 20px;
    }

    .gap {
        margin-right: 20px;
    }

    .right {
        .lang {
            font-size: 14px;

            .item {
                cursor: pointer;

                &.active {
                    font-size: 18px;
                    font-weight: bold;
                }
            }
        }
    }

    .el-dropdown {
        color: inherit;
    }
}
</style>