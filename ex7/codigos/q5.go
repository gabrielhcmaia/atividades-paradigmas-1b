package main

import "fmt"

func main() {
	x := 10
	if x > 5 {
		x := x * 2
		fmt.Println("dentro:", x)
	}
	fmt.Println("fora:", x)
}
