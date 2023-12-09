package com.example.myapplication.date;

import org.litepal.crud.LitePalSupport;

import java.util.Objects;

public class Title{

    public Title(String category,boolean isExpand) {
        this.isExpand = isExpand;
        this.category = category;
    }
//    这段代码是一个构造函数，用于创建名为 `Title` 的类的实例。构造函数在实例化类时被调用，用于初始化类的实例变量。
//这个构造函数接受两个参数：`category` 和 `isExpand`。在构造函数内部，这些参数被用来初始化类的实例变量 `this.isExpand` 和 `this.category`。
//- `category` 参数是一个字符串，用于表示标题的类别或类别名称。
//- `isExpand` 参数是一个布尔值，用于表示标题是否展开的状态。
//因此，当你调用这个构造函数来创建 `Title` 类的实例时，你需要传入一个字符串作为类别，以及一个布尔值来表示是否展开。这样就可以初始化一个具有指定类别和展开状态的 `Title` 对象。

    private boolean isExpand = false;

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return Objects.equals(category, title.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }
}
