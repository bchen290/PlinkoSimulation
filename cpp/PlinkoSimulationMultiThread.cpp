#include <iostream>
#include <random>
#include <string>
#include <chrono>

const long NUMBER_OF_TRIALS = 1000000000;
const int NUMBER_OF_LOCATIONS = 9;

const int BOARD_HEIGHT = 12;
const int BOARD_WIDTH = 16;

const int WINNER_COLUMN = 8;
const int WINNER_ROW = 12;

struct thread_data {
    int locationNumber;
    int *locationWins;
};

void *simulate(void *threadarg) {
    struct thread_data *data;
    data = (struct thread_data *) threadarg;

    int chipRow = 0;
    int chipColumn = 0;
    int randomNumber = 0;

    const auto now = std::chrono::system_clock::now();
    const auto epoch = now.time_since_epoch();
    const auto seconds = std::chrono::duration_cast<std::chrono::seconds>(epoch);
    static uint32_t x = seconds.count();
    static uint32_t y = 362436069;
    static uint32_t z = 521288629;
    static uint32_t w = 88675123;

    for(int j = 0; j < NUMBER_OF_TRIALS; j++){
        chipRow = 0;
        chipColumn = data->locationNumber * 2;

        for(int k = 0; k < BOARD_HEIGHT; k++){
            if(chipColumn == 0){
                randomNumber = 1;
            }else if(chipColumn == BOARD_WIDTH){
                randomNumber = 0;
            }else{
                uint32_t t;
                t = x ^ (x << 11);
                x = y; y = z; z = w;
                w = w ^ (w >> 19) ^ (t ^ (t >> 8));
                randomNumber = w;
            }

            chipRow += 1;
            chipColumn += randomNumber > 0 ? 1 : -1;
        }

        if(chipRow == WINNER_ROW && chipColumn == WINNER_COLUMN){
            data->locationWins[data->locationNumber]++;
        }
    }

    pthread_exit(NULL);
}

int main() {
    std::cout << "Running for " << NUMBER_OF_TRIALS << " trials" << std::endl;

    char locations[NUMBER_OF_LOCATIONS] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
    int locationWins[NUMBER_OF_LOCATIONS] = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    pthread_t threads[NUMBER_OF_LOCATIONS];
    struct thread_data td[NUMBER_OF_LOCATIONS];
    pthread_attr_t attr;
    void *status;

    auto startTime = std::chrono::high_resolution_clock::now();

    for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
        td[i].locationNumber = i;
        td[i].locationWins = locationWins;
        pthread_create(&threads[i], NULL, simulate, (void *)&td[i]);
    }

    pthread_attr_destroy(&attr);
    for (unsigned long thread : threads) {
        pthread_join(thread, &status);
    }

    auto endTime = std::chrono::high_resolution_clock::now();

    for(int i = 0; i < NUMBER_OF_LOCATIONS; i++){
        std::cout << "Probability of " << locations[i] << " winning is " << (double)locationWins[i]/NUMBER_OF_TRIALS << std::endl;
    }

    std::cout << "Multi thread took "
              << std::chrono::duration_cast<std::chrono::nanoseconds>(endTime-startTime).count()/1e+9
              << " seconds";

    return 0;
}
