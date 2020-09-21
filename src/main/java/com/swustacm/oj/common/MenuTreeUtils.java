package com.swustacm.oj.common;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 树状结构生成器
 * @author xingzi
 * @date 2019 07 14  10:00
 */
public class MenuTreeUtils<T> {

    private List<T> treeList;
    //获取fid 的方法名
    private String getFidMethod = "getFid";
    //获取id 的方法名
    private String getIdMethod = "getId";
    //setChildren 的方法名
    private String setChildrenMethod = "setChildren";
    //根节点
    private String root = "0";
    /**
     * 向下子节点列表
     */
    private List<T> downList = new ArrayList<>();
    /**
     * 向上子节点列表
     */
    private List<T> upList = new ArrayList<>();

    public MenuTreeUtils(List<T> list) {
        this.treeList = list;
    }

    /**
     * @param list              列表
     * @param getFidMethod      获取父id的方法名称
     * @param getIdMethod       获取id的方法名称
     * @param setChildrenMethod 设定子树的方法名称
     * @param root              根节点
     */
    public MenuTreeUtils(List<T> list, String getFidMethod, String getIdMethod, String setChildrenMethod, String root) {
        this.treeList = list;
        this.getFidMethod = getFidMethod;
        this.getIdMethod = getIdMethod;
        this.setChildrenMethod = setChildrenMethod;
        this.root = root;
    }

    /**
     *构建树状结构
     * @return
     */
    public List<T> buildTree(boolean downList) {
        List<T> treeTs = new ArrayList<>();
        for (T tNode : Objects.requireNonNull(getRootNode(root))) {
            if(downList){
                this.downList.add(tNode);
            }
            else {
                this.upList.add(tNode);
            }
            buildChildTree(tNode, downList);
            treeTs.add(tNode);
        }
        return treeTs;
    }
    /**
     * 获取向上向下的子树列表
     * @param downList 是否获取向下的列表
     * @return
     */
    public List<T> getList(boolean downList) {
        if(this.downList.isEmpty() && this.upList.isEmpty()) {
            buildTree(downList);
        }
        setChildNull(this.downList);
        setChildNull(this.upList);
        return downList ? this.downList : this.upList;
    }

    /**
     * 获取向上向下的子树列表
     * @param downList 是否获取向下的列表
     * @param childNull 子树设置空
     * @return
     */
    public List<T> getList(boolean downList,boolean childNull) {
        if(this.downList.isEmpty() && this.upList.isEmpty()) {
            buildTree(downList);
        }
        if(childNull) {
            setChildNull(this.downList);
            setChildNull(this.upList);
        }
        return downList ? this.downList : this.upList;
    }
    /**
     * 将children设置为null
     * @param tNode 数据列表
     * @return
     */
    private void setChildNull(List<T> tNode){
        for(T node : tNode) {
            try {
                Method method = node.getClass().getDeclaredMethod(setChildrenMethod, List.class);
                method.invoke(node, Collections.EMPTY_LIST);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向下构建子树
     *
     * @param pNode 树节点
     * @return 子树
     */
    private T buildChildTree(T pNode, boolean downList) {
        try {
            List<T> childTs = new ArrayList<>();
            for (T tNode : treeList) {
                //如果是向下遍历 获取fid 是否为pNode的id 向上 获取Id是否为pNode的fid
                Method method = tNode.getClass().getMethod(downList ? getFidMethod : getIdMethod);
                String fid = String.valueOf(method.invoke(tNode));
                String id = String.valueOf(pNode.getClass().getMethod(downList ? getIdMethod : getFidMethod).invoke(pNode));
                if (fid.equals(id)) {
                    if(downList){
                        this.downList.add(tNode);
                    }
                    else {
                        this.upList.add(tNode);
                    }
                    childTs.add(buildChildTree(tNode, downList));
                }
            }
            if (childTs.isEmpty()) {
                return pNode;
            }
            //为pNote 设定子节点的值
            Method method = pNode.getClass().getDeclaredMethod(setChildrenMethod, List.class);
            method.invoke(pNode, childTs);
            return pNode;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return pNode;
        }
    }

    /**
     * 获取根节点
     *
     * @return 根节点列表
     */
    private List<T> getRootNode(String root) {
        try {
            List<T> rootLists = new ArrayList<T>();
            for (T tNode : treeList) {
                Method method = tNode.getClass().getMethod(getIdMethod);
                String id = String.valueOf(method.invoke(tNode));
                if (id.equals(root)) {
                    rootLists.add(tNode);
                }
            }
            return rootLists;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
