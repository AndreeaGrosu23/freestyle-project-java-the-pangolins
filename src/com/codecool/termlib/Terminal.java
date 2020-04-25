package com.codecool.termlib;

import java.util.*;

public class Terminal {
    /**
     * The beginning of control sequences.
     */
    // HINT: In \033 the '0' means it's an octal number. And 33 in octal equals 0x1B in hexadecimal.
    // Now you have some info to decode that page where the control codes are explained ;)
    private static final String CONTROL_CODE = "\033[";
    /**
     * Command for whole screen clearing.
     *
     * Might be partitioned if needed.
     */
    private static final String CLEAR = "2J";
    /**
     * Command for moving the cursor.
     */
    private static final String MOVE = "H";
    /**
     * Command for printing style settings.
     *
     * Handles foreground color, background color, and any other
     * styles, for example color brightness, or underlines.
     */
    private static final String STYLE = "m";

    /**
     * Reset printing rules in effect to terminal defaults.
     *
     * Reset the color, background color, and any other style
     * (i.e.: underlined, dim, bright) to the terminal defaults.
     */
    public void resetStyle() {
        System.out.println(CONTROL_CODE + 0 + STYLE);
    }

    /**
     * Clear the whole screen.
     *
     * Might reset cursor position.
     */
    public void clearScreen() {
        System.out.println(CONTROL_CODE + CLEAR);
    }

    /**
     * Move cursor to the given position.
     *
     * Positions are counted from one.  Cursor position 1,1 is at
     * the top left corner of the screen.
     *
     * @param x Column number.
     * @param y Row number.
     */
    public void moveTo(Integer x, Integer y) {
        System.out.print(CONTROL_CODE+x+';'+y+'f');
    }

    /**
     * Set the foreground printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        switch(color) {
            case BLACK:
                System.out.print("\033[30m");
                break;
            case RED:
                System.out.print("\033[31m");
                break;
            case GREEN:
                System.out.print("\033[32m");
                break;
            case YELLOW:
                System.out.print("\033[33m");
                break;
            case BLUE:
                System.out.print("\033[34m");
                break;
            case MAGENTA:
                System.out.print("\033[35m");
                break;
            case CYAN:
                System.out.print("\033[36m");
                break;
            case WHITE:
                System.out.print("\033[37m");
                break;
        }
    }

    /**
     * Set the background printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The background color to set.
     */
    public void setBgColor(Color color) {
        switch(color) {
            case BLACK:
                System.out.print("\033[40m");
                break;
            case RED:
                System.out.print("\033[41m");
                break;
            case GREEN:
                System.out.print("\033[42m");
                break;
            case YELLOW:
                System.out.print("\033[43m");
                break;
            case BLUE:
                System.out.print("\033[44m");
                break;
            case MAGENTA:
                System.out.print("\033[45m");
                break;
            case CYAN:
                System.out.print("\033[46m");
                break;
            case WHITE:
                System.out.print("\033[47m");
                break;
        }
    }

    /**
     * Make printed text underlined.
     *
     * On some terminals this might produce slanted text instead of
     * underlined.  Cannot be turned off without turning off colors as
     * well.
     */
    public void setUnderline() {
        System.out.print("\033[4m");
    }

    /**
     * Move the cursor relatively.
     *
     * Move the cursor amount from its current position in the given
     * direction.
     *
     * @param direction Step the cursor in this direction.
     * @param amount Step the cursor this many times.
     */
    public void moveCursor(Direction direction, Integer amount) {
        switch (direction){
            case UP:
                System.out.println(CONTROL_CODE + amount + "A");
                break;
            case DOWN:
                System.out.println(CONTROL_CODE + amount + "B");
                break;
            case FORWARD:
                System.out.println(CONTROL_CODE + amount + "C");
                break;
            case BACKWARD:
                System.out.println(CONTROL_CODE + amount + "D");
                break;
        }
    }

    /**
     * Set the character diplayed under the current cursor position.
     *
     * The actual cursor position after calling this method is the
     * same as beforehand.  This method is useful for drawing shapes
     * (for example frame borders) with cursor movement.
     *
     * @param c the literal character to set for the current cursor
     * position.
     */
    public void setChar(char c) {
        // Save the cursor position
        System.out.println(CONTROL_CODE + "s");
        // Move the cursor
        moveCursor(Direction.UP, 5); //Needs moveTo function
        // Display the character
        System.out.println(c);
        // Restores cursor position
        System.out.println("\033"+ "7");
    }

    /**
     * The ony scope for this function is to
     * @return Black Heart Suit Emoji
     */
    public char getGlyph(){
        char glyph = '\u2665';
        return glyph;
    }

    /**
     * Helper function for sending commands to the terminal.
     *
     * The common parts of different commands shall be assembled here.
     * The actual printing shall be handled from this command.
     *
     * @param commandString The unique part of a command sequence.
     */
    private void command(String commandString) {

        List<String> userInputList = new ArrayList<String>(Arrays.asList(commandString.split(" ")));

        Scanner scanner = new Scanner(System.in);

        if (commandString.equals("0")){
            System.out.println("Thank you for your visit !");
            System.out.println("Please don't come again");
            System.exit(0);
        }
        else if (commandString.equals("1")){
            clearScreen();
        }
        else if (userInputList.get(0).toLowerCase().equals("2")){
            if (userInputList.size() == 3) {
                String color = userInputList.get(2).toLowerCase();
                if (userInputList.get(1).toLowerCase().equals("bgcolor")) {
                    if (color.equals("black") || color.equals("red") || color.equals("yellow") || color.equals("green") || color.equals("blue") || color.equals("cyan") || color.equals("magenta") || color.equals("white")) {
                        color = userInputList.get(2).toUpperCase();
                        setBgColor(Color.valueOf(color));
                    } else {
                        System.out.println("Invalid command");
                        main(null);
                    }
                } else if (userInputList.get(1).toLowerCase().equals("fgcolor")) {
                    if (color.equals("black") || color.equals("red") || color.equals("yellow") || color.equals("green") || color.equals("blue") || color.equals("cyan") || color.equals("magenta") || color.equals("white")) {
                        color = userInputList.get(2).toUpperCase();
                        setColor(Color.valueOf(color));
                    } else {
                        System.out.println("Invalid command");
                        main(null);
                    }
                } else {
                    System.out.println("Invalid command");
                    main(null);
                }
            } else {
                 System.out.println("Invalid command");
                 main(null);
            }
        }
        else if (commandString.equals("3")){
            resetStyle();
        }
        else if (commandString.equals("5")){
            System.out.println("Direction: up , down, forward, backward");
            String userDirection = scanner.nextLine().toUpperCase();
            Direction resultDirection = null;
            if (userDirection.equals("UP")){
                resultDirection = Direction.UP;
            }else if (userDirection.equals("DOWN")){
                resultDirection = Direction.DOWN;
            }else if (userDirection.equals("FORWARD")){
                resultDirection = Direction.FORWARD;
            }else if (userDirection.equals("BACKWARD")){
                resultDirection = Direction.BACKWARD;
            }else {
                System.out.println("Write correct the direction");
                main(null);
            }
            System.out.println("Amount");
            Integer userAmount = scanner.nextInt();
            moveCursor(resultDirection,userAmount);
        }
        else if (commandString.equals("6")){
            System.out.println("Write the character:");
            char userChar = scanner.nextLine().charAt(0);
            clearScreen();
            setChar(userChar);
        }
        else if (commandString.equals("7")){
            clearScreen();
            setChar(getGlyph());
        }
        else if (commandString.equals("8")){
            setUnderline();
        }
        else if (commandString.equals("99")){
            menuList();
        }
    }


    /**
     * Display program commands
     */
    public static void menuList(){
        System.out.println();
        System.out.println("Welcome to the terminal emulator of The Pangolins");
        System.out.println("Below is the menu, feel free to choose anything!");
        System.out.println();
        System.out.println("1. Clear screen");
        System.out.println("2. Set color (choose bgcolor for background color and fgcolor for foreground color");
        System.out.println("Color options: black, red, yellow, green, blue, cyan, magenta, white");
        System.out.println("3. Reset display settings");
        System.out.println("4. Create special symbol");
        System.out.println("5. Move the cursor");
        System.out.println("6. Display character cursor position");
        System.out.println("7. Display Glyph");
        System.out.println("8. Underline text");
        System.out.println();
        System.out.println("99.The menu");
        System.out.println("0. Exit the program");
        System.out.println();
    }

    /**
     * The main function it execute the menuList
     * @param args null
     */
    public static void main(String[] args) {

        Scanner myMenu = new Scanner(System.in);  // Create a Scanner object
        Terminal terminal = new Terminal();
        Boolean runMenu = true;
        menuList();

        while (runMenu){
            System.out.println("Your choice is:");
            String optionSelected = myMenu.nextLine();  // Read user input
            terminal.command(optionSelected);
        }

    }

}
