package com.bombproj.framework

import com.bombproj.framework.SpringContextHolder.context
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.stereotype.Component

@Component
class SpringContextHolder extends ApplicationContextAware {

    override def setApplicationContext(applicationContext: ApplicationContext): Unit = {
        context = applicationContext
    }

}

object SpringContextHolder {
    private var context: ApplicationContext = _

    def getBean[T](clazz: Class[T]): T = {
        context.getBean(clazz)
    }
}
