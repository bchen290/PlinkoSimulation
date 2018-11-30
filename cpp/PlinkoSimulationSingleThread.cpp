/*
#include <iostream>
#include <random>
#include <string>
#include <chrono>

const int NUMBER_OF_TRIALS = 1000000;
const int NUMBER_OF_LOCATIONS = 9;

const int BOARD_HEIGHT = 12;
const int BOARD_WIDTH = 16;

const int WINNER_COLUMN = 8;
const int WINNER_ROW = 12;

uint32_t xor128() {
    static uint32_t x = 123456789;
    static uint32_t y = 362436069;
    static uint32_t z = 521288629;
    static uint32_t w = 88675123;
    uint32_t t;
    t = x ^ (x << 11);
    x = y; y = z; z = w;
    return w = w ^ (w >> 19) ^ (t ^ (t >> 8));
}

int main() {
    std::cout << "Running for " << NUMBER_OF_TRIALS << " trials" << std::endl;

    char locations[NUMBER_OF_LOCATIONS] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
    int locationWins[NUMBER_OF_LOCATIONS] = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    int chipRow = 0;
    int chipColumn = 0;
    int randomNumber = 0;

    auto startTime = std::chrono::high_resolution_clock::now();

    for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
        for(int j = 0; j < NUMBER_OF_TRIALS; j++){
            chipRow = 0;
            chipColumn = i * 2;

            for(int k = 0; k < BOARD_HEIGHT; k++){
                if(chipColumn == 0){
                    randomNumber = 1;
                }else if(chipColumn == BOARD_WIDTH){
                    randomNumber = 0;
                }else{
                    randomNumber = xor128();
                }

                chipRow += 1;
                chipColumn += randomNumber > 0 ? 1 : -1;
            }

            if(chipRow == WINNER_ROW && chipColumn == WINNER_COLUMN){
                locationWins[i]++;
            }
        }
    }

    auto endTime = std::chrono::high_resolution_clock::now();

    for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
        std::cout << "Probability of " << locations[i] << " winning is " << (double)locationWins[i]/NUMBER_OF_TRIALS << std::endl;
    }

    std::cout << "Single thread took "
              << std::chrono::duration_cast<std::chrono::nanoseconds>(endTime-startTime).count()/1e+9
              << " seconds";

    return 0;
}*/
