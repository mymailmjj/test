/**
 * 
 */
package datasource;

/**
 * @author cango
 *
 */
public enum PooledObjectStatus {

    //刚刚创建还未被使用过
    CREATED("created,", 1),
    //对象在使用中
    USING("using", 2),
    //对象在空闲中
    IDLE("idle",3),
    //关闭中
    CLOSING("closing",4);

    private String name;

    private int state;

    PooledObjectStatus(String name, int state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

}
