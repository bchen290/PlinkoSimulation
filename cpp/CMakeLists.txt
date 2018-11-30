cmake_minimum_required(VERSION 3.5)
project(PlinkoCPP)

set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

add_executable(PlinkoCPP PlinkoSimulationSingleThread.cpp PlinkoSimulationMultiThread.cpp)

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)
target_link_libraries(PlinkoCPP Threads::Threads)