import calendar
import time

NUMBER_OF_TRIALS = 1000
NUMBER_OF_LOCATIONS = 9

BOARD_HEIGHT = 12
BOARD_WIDTH = 16

WINNER_COLUMN = 8
WINNER_ROW = 12

print("Running for " + str(NUMBER_OF_TRIALS) + " trials")

locations = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I']
locationWins = [0, 0, 0, 0, 0, 0, 0, 0, 0]

chip_row = 0
chip_column = 0
random_number = 0


class XORRng:
    x = calendar.timegm(time.gmtime())
    y = 362436069
    z = 521288629
    w = 88675123

    def xor128(self):
        t = self.x ^ (self.x << 11)
        self.x = self.y
        self.y = self.z
        self.z = self.w
        self.w = self.w ^ (self.w >> 19) ^ (t ^ (t >> 8))
        return self.w


random = XORRng()

start = time.perf_counter()

for i in range(NUMBER_OF_LOCATIONS):
    for j in range(NUMBER_OF_TRIALS):
        chip_row = 0
        chip_column = i * 2

        for k in range(BOARD_HEIGHT):
            if chip_column == 0:
                random_number = 1
            elif chip_column == BOARD_WIDTH:
                random_number = -1
            else:
                random_number = random.xor128()

            chip_row += 1
            chip_column += 1 if random_number > 0 else -1

        if chip_row == WINNER_ROW and chip_column == WINNER_COLUMN:
            locationWins[i] += 1

end = time.perf_counter()

for i in range(NUMBER_OF_LOCATIONS):
    print("Probability of " + locations[i] + " winning is " + str(float(locationWins[i])/float(NUMBER_OF_TRIALS)))

print("Single Thread took " + str(end - start) + " seconds")
