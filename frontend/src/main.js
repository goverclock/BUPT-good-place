import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import * as ElIcons from '@element-plus/icons-vue'
import 'normalize.css'

const app = createApp(App)
for (const name in ElIcons) {
    app.component(name, ElIcons[name])
}
app.use(store).use(router).mount('#app')
