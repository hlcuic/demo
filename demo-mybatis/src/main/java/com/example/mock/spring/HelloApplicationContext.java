package com.example.mock.spring;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.net.URL;
import java.util.Map;

/**
 * @author cuihailong
 * @date 2022/9/29 14:00
 */
public class HelloApplicationContext extends AbstractHelloApplicationContext{

    public HelloApplicationContext(Class clazz) {
        super(clazz);

        // 刷新上下文
        refreshContext();

        // 预实例化对象
        preInstantiate();
    }

    private void refreshContext(){
        HelloComponentScan helloComponentScanAnno = AnnotationUtils.getAnnotation(clazz,HelloComponentScan.class);
        if(helloComponentScanAnno != null){
            String scanPath = helloComponentScanAnno.value();
            String relativePath = scanPath.replaceAll("\\.","/");

            URL url = HelloApplicationContext.class.getClassLoader().getResource(relativePath);
            File file = new File(url.getFile());
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File f :files){
                    try {
                        String absolutePath = f.getAbsolutePath();
                        String classPath = f.getAbsolutePath().substring(absolutePath.indexOf("com"),absolutePath.indexOf(".class"));
                        Class clazz = HelloApplicationContext.class.getClassLoader().loadClass(classPath.replace("\\","."));

                        HelloBeanDefinition helloBeanDefinition = new HelloBeanDefinition();
                        helloBeanDefinition.setClazz(clazz);

                        HelloComponent helloComponent = (HelloComponent) clazz.getAnnotation(HelloComponent.class);
                        if(helloComponent != null){
                            String beanName = helloComponent.value();
                            beanDefinitionMap.put(beanName,helloBeanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }else{

            }

        }
    }

    private void preInstantiate(){
        if(CollectionUtils.isEmpty(beanDefinitionMap)){
            return;
        }

        for(Map.Entry<String,HelloBeanDefinition> entry : beanDefinitionMap.entrySet()){
            String beanName = entry.getKey();
            HelloBeanDefinition helloBeanDefinition = entry.getValue();
            Class clazz = helloBeanDefinition.getClazz();
            try {
                Object bean = clazz.newInstance();
                singletonMap.put(beanName,bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Object getBean(String beanName){
        return singletonMap.get(beanName);
    }

}
