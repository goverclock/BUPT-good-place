<template>
    <div class="header-cont">
        <div class="left">
            <h1>
                <router-link to="/">BUPT好去处</router-link>
            </h1>
        </div>
        <div class="right flex-center">
            <template v-if="isLogin">
                <el-dropdown trigger="click" @command="handleCommand">
                    <div class="flex-center cursor">
                        {{ username }}
                        <el-icon><caret-bottom /></el-icon>
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="toUser">个人中心</el-dropdown-item>
                            <el-dropdown-item divided command="toLogout">退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </template>
        </div>
    </div>
</template>

<script setup>
import { storeKey } from 'vuex';

const store = useStore();
const router = useRouter();

const commands = ({
    toUser: () => {
        router.push('/user/detail')
    },
    toLogout: () => {
        store.commit('user/clearToken')
        store.commit('user/clearUserInfo')
        router.push('/login')
        location.reload()
    }
});

function handleCommand(cmd) {
    commands[cmd] && commands[cmd]();
}

const isLogin = computed(() => store.getters['user/isLogin']);
let username = computed(() => {
    if (isLogin.value) {
        let userInfo = store.getters['user/userInfo'];
        return userInfo?.user_id
    }
    return "未登录"
})
</script>

<style lang="scss">
.header-cont {
    background-color: #f5f7f9;
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