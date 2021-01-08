package com.bombproj.framework

import com.bombproj.framework.SpringContextHolder.context
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.stereotype.Component

@Component
class SpringContextHolder extends ApplicationContextAware {

    override def setApplicationContext(applicationContext: ApplicationContext): Unit = {
        context = applicationContext
    }

    def getBean[T](clazz: Class[T]): T = {
        context.getBean(clazz)
    }

}

private object SpringContextHolder {
    private var context: ApplicationContext = _
}
