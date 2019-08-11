package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

    private static int width;
    private static int height;

    private static Maze maze;

    private static Random randGen = new Random();
    private static Stack<Cell> uncheckedCells = new Stack<Cell>();

    public static Maze generateMaze(int w, int h) {
        width = w;
        height = h;
        maze = new Maze( width, height );

        // 4. select a random cell to start
        Cell startCell = maze.getCell( randGen.nextInt(w), randGen.nextInt(h) );
        
        Cell enterCell = maze.getCell( 0, randGen.nextInt(h) );
        Cell exitCell  = maze.getCell( 4, randGen.nextInt(h) );
        
        enterCell.setWestWall( false );
        exitCell.setEastWall( false );

        // 5. call selectNextPath method with the randomly selected cell
        selectNextPath( startCell );

        return maze;
    }

    // 6. Complete the selectNextPathMethod
    private static void selectNextPath(Cell currentCell) {
        // A. mark cell as visited
        currentCell.setBeenVisited( true );

        // B. check for unvisited neighbors using the cell
        ArrayList<Cell> unvisitedNeighbors = getUnvisitedNeighbors( currentCell );

        // C. if has unvisited neighbors,
        if( unvisitedNeighbors.size() > 0 ) {

            // C1. select one at random.
            Cell randomCell = unvisitedNeighbors.get( randGen.nextInt( unvisitedNeighbors.size() ) );

            // Debug statements
            System.out.println( "curr   cell: " + currentCell.getX() + " " + currentCell.getY() );
            System.out.println( "random cell: " + randomCell.getX()  + " " + randomCell.getY() );

            // C2. push it to the stack
            uncheckedCells.push( randomCell );

            // C3. remove the wall between the two cells
            removeWalls( randomCell, currentCell );

            // C4. make the new cell the current cell and mark it as visited
            currentCell = randomCell;

            // C5. call the selectNextPath method with the current cell
            selectNextPath( currentCell );

        } else {

            // D. if all neighbors are visited

            // D1. if the stack is not empty
            if( !uncheckedCells.empty() ) {
                // D1a. pop a cell from the stack
                currentCell = uncheckedCells.pop();
                // D1b. make that the current cell

                // D1c. call the selectNextPath method with the current cell
                selectNextPath( currentCell );
            }
        }

    }

    // 7. Complete the remove walls method.
    // This method will check if c1 and c2 are adjacent.
    // If they are, the walls between them are removed.
    private static void removeWalls(Cell c1, Cell c2) {
        if( c1.getX() == c2.getX() + 1 ) {
            c1.setWestWall( false );
            c2.setEastWall( false );
        } else if( c1.getX() == c2.getX() - 1 ) {
            c1.setEastWall( false );
            c2.setWestWall( false );
        }

        if( c1.getY() == c2.getY() + 1 ) {
            c1.setNorthWall( false );
            c2.setSouthWall( false );
        } else if( c1.getY() == c2.getY() - 1 ) {
            c1.setSouthWall( false );
            c2.setNorthWall( false );
        }
    }

    // 8. Complete the getUnvisitedNeighbors method
    // Any unvisited neighbor of the passed in cell gets added
    // to the ArrayList
    private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
        ArrayList<Cell> unvisitedNeighbors = new ArrayList<Cell>();
        Cell neighbor;

        neighbor = maze.getCell( c.getX() + 1, c.getY() );
        if( ( neighbor != null ) && !neighbor.hasBeenVisited() ) {
            unvisitedNeighbors.add( neighbor );
        }

        neighbor = maze.getCell( c.getX() - 1, c.getY() );
        if( ( neighbor != null ) && !neighbor.hasBeenVisited() ) {
            unvisitedNeighbors.add( neighbor );
        }

        neighbor = maze.getCell( c.getX(), c.getY() + 1 );
        if( ( neighbor != null ) && !neighbor.hasBeenVisited() ) {
            unvisitedNeighbors.add( neighbor );
        }

        neighbor = maze.getCell( c.getX(), c.getY() - 1 );
        if( ( neighbor != null ) && !neighbor.hasBeenVisited() ) {
            unvisitedNeighbors.add( neighbor );
        }

        return unvisitedNeighbors;
    }
}
