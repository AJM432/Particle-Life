<div align="center">
<img style="border-radius: 10px" width="400" height="400" src="https://github.com/user-attachments/assets/e29b3449-b2be-4613-ab47-3cbe8f202f7e">
</div>

# Particle Life

Simulate complex life-like behaviour from simple rules!

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white)

## Demo
https://github.com/AJM432/Particle-Life/assets/49791407/a0ba73ab-2706-4021-b941-0f73b30c91bb

## Implementation
1. We define a force function dependent on the distance between particles $r$ and the species of the two particles $i$ and $j$ defined by

$$
f(r, i, j) = 
\begin{cases} 
      \frac{r}{\beta} - 1 & r\leq \beta \\
      A_{ij}\cdot(1-|\frac{2r-1-\beta}{1-\beta}|) & \beta \leq r\leq 1 \\
      0 & \text{otherwise}
   \end{cases}
$$

where $\beta=0.3$ and $A$ is an $N \times N$ matrix that defines the pairwise attraction multiplier between $N$ different species.

2. However, we only apply the force if the two particles are within a certain threshold distance $r < r_{forceRange}$. This causes localized behaviour leading to particles behaving in an interesting life-like manner.

3. Next, we sum these forces using an n-body approach where the force exerted on a single particle $i$ is defined by

$$f_i = r_{forceRange}\cdot\sum_{j}\hat{r}\cdot f(\frac{r_{ij}}{r_{forceRange}}, i, j), \text{ as } f \text{ expects } r \in [0, 1]$$

4. Lastly, we apply eulerian integration
$$a = f_i \text{ asuming the mass of a particle is 1}$$
$$v_t = v_{t-1} + a\Delta t\cdot\mu \text{ where } \mu \text{ is a friction coefficient defined by } \mu = (\frac{1}{2})^{\frac{\Delta t}{t_{half}}}$$
$$d_t = d_{t-1} + v\Delta t + (\frac{1}{2})a\Delta t^2$$

## Features

- View interactions between life-like particles in real-time
- Interactive zoom controls to observe phenomena more closely
- Tuneable parameters in a Java config file

## Usage/Examples
Change starting parameters to observe new life forms and phenomenon.

```java
	package com.example.particlelife;
	public final class Constants {
	    public static final int numSpecies = 8;
	    public static final int numParticles = 1000;
	    public static final int particleSize = 2;
	    public static final double forceRange = 100;
	    public static final double tHalf = 0.05; // after how much time exactly half the velocity will be lost to friction
	    public static final int phaseSize = 10;
	    public static final int HEIGHT = 800;
	    public static final int WIDTH = 1400;
}
```

## Install & Run
Ensure you have Java, JavaFX and Maven installed.

Clone the project

```bash
git clone https://github.com/AJM432/Particle-Life.git

```


Go to the project directory

```bash
cd Particle-Life
```

Build and run the project

```bash
mvn clean javafx:run
```

## Feedback

If you have any feedback, please reach out to me at alvinjosematthew@gmail.com

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://alvinmatthew.com/)
