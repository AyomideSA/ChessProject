package Model;

/**
 * @author : ayoso
 * @mailto : ayomide.sola-ayodele@ucdconnect.ie
 * @since : 22/07/2022, Friday
 **/
public class Location {

  private int column;
  private int row;

  public Location(int row, int column) {
    this.column = column;
    this.row = row;
  }

  @Override
  public boolean equals(Object o) {

    if (!(o instanceof Location)) {
      return false;
    }

    Location location = (Location) o;

    return this.column == location.column && this.row == location.row;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

}
