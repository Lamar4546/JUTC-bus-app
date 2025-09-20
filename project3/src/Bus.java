package src;
/**
 * Represents a Bus object.
 */
public class Bus {
  private String name;
  private String id;
  private String route;
  private int capacity;
  private String driver;

  /**
   * Constructs a Bus object with the specified attributes.
   *
   * @param name     the name of the bus
   * @param id       the ID of the bus
   * @param route    the route of the bus
   * @param capacity the capacity of the bus
   * @param driver   the driver of the bus
   */
  public Bus(String name, String id, String route, int capacity, String driver) {
    this.name = name;
    this.id = id;
    this.route = route;
    this.capacity = capacity;
    this.driver = driver;
  }

  /**
   * Sets the name of the bus.
   *
   * @param name the name of the bus
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the ID of the bus.
   *
   * @param id the ID of the bus
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Sets the route of the bus.
   *
   * @param route the route of the bus
   */
  public void setRoute(String route) {
    this.route = route;
  }

  /**
   * Sets the capacity of the bus.
   *
   * @param capacity the capacity of the bus
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  /**
   * Sets the driver of the bus.
   *
   * @param driver the driver of the bus
   */
  public void setDriver(String driver) {
    this.driver = driver;
  }

  /**
   * Returns the name of the bus.
   *
   * @return the name of the bus
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the ID of the bus.
   *
   * @return the ID of the bus
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the route of the bus.
   *
   * @return the route of the bus
   */
  public String getRoute() {
    return route;
  }

  /**
   * Returns the capacity of the bus.
   *
   * @return the capacity of the bus
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Returns the driver of the bus.
   *
   * @return the driver of the bus
   */
  public String getDriver() {
    return driver;
  }
}