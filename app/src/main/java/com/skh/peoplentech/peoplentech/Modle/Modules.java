package com.skh.peoplentech.peoplentech.Modle;

public class Modules {

    private String moduleContent;
    private String moduleName;

    public Modules(){

    }

    public Modules(String moduleName, String moduleContent){
        this.moduleName = moduleName;
        this.moduleContent = moduleContent;
    }

    public String getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(String moduleContent) {
        this.moduleContent = moduleContent;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
