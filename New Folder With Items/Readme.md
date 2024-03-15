<div align="center">
<img width="250" height="250" src="https://github.com/AJM432/Particle-Life/assets/49791407/0324f431-ed9c-4794-a94c-50ce7c7dcd51">
</div>

# Particle Life

Simulate complex life-like behaviour from simple rules!


![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

## Demo
https://github.com/AJM432/Star-Swift/assets/49791407/d7a6447f-662e-40e2-b7d3-4137854bb5e9

## Features

- View stellar dynamics in real-time
- Tunable system parameters for experimentation
- View system variables such as velocity and acceleration in real-time
- View kinetic energy graph during system evolution
- Fullscreen mode

## Usage/Examples
Manually change starting parameters to observe different initial galaxy configurations.
```cpp
// Determine galaxy size and shape
const int NUM_STARS = 5000;
const int RADIUS = 300;
...
// Determines the shape of the galaxy, currently a perfect circle
std::uniform_real_distribution<double> dist_pos_y(-y_variance+height_middle, y_variance+height_middle);
```


## Run Locally
`Ensure you have g++ and make installed`

Clone the project

```bash
  git clone https://github.com/AJM432/Star-Swift.github.io.git
```

Go to the project directory

```bash
  cd Star-Swift
```

Build the project

```bash
  make
```

Run StarSwift

```bash
  ./StarSwift
```


## Tech Stack
**Graphics and Window Handling**: SDL2 (Simple DirectMedia Layer)

**GUI Framework**: ImGui (Immediate mode GUI library for C++)

**Plotting**: ImPlot (Extension for ImGui for real-time plotting)

**Programming Language**: C++
## Feedback

If you have any feedback, please reach out to me at alvinjosematthew@gmail.com
## Acknowledgements
Here are some excellent articles describing how the Barnes-Hut algorithm works!
- [The Barnes-Hut Algorithm: Tom Ventimiglia & Kevin Wayne](http://arborjs.org/docs/barnes-hut)
- [The Barnes-Hut Approximation: Efficient computation of N-body forces By Jeffrey Heer](https://jheer.github.io/barnes-hut/)


## License

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://alvinmatthew.com/)

[![SolarSynesthesia](https://img.shields.io/badge/Solar-Synesthesia-20B2AA?style=for-the-badge)](https://github.com/AJM432/Solar-Synesthesia)

[![SolarSynesthesia](https://img.shields.io/badge/Solar%20System-Simulator-20B2AA?style=for-the-badge)](https://github.com/AJM432/Solar-System-Simulator)
