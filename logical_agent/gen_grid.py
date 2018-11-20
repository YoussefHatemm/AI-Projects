from enum import Enum
from random import randint
class Occupant(Enum):
    FREE = 1
    OBSTACLE = 2
    WALKER = 3

def gen_grid_atoms(dimensions):
    file = open('grid.pl', 'w')
    
    walkersAmount = 0

    width, height = dimensions


    file.write('width({}).\n'.format(width))
    file.write('height({}).\n'.format(height))
    file.write('jon(1,1,s0).\n')
    file.write('maxAmmo({}).\n'.format(randint(1,20)))
    
    dragonX = randint(2,width)
    dragonY = randint(2, height)
    
    file.write('dragonStone({},{}).\n'.format(dragonX,dragonY))

    for i in range(1, width + 1):
        for j in range(1, height + 1):
            if (i == dragonX and j == dragonY) or (i == 1 and j ==1):
                continue

            option = Occupant(randint(1,3))

            if option == Occupant.WALKER:
                file.write('walker({},{},s0).\n'.format(i, j))
                walkersAmount += 1

            elif option == Occupant.OBSTACLE:
                file.write('obstacle({},{}).\n'.format(i, j))

    file.write("walkersAlive({},s0).\n".format(walkersAmount))
    file.close()

if __name__ == "__main__":
    gen_grid_atoms((3,3))