import os
import pandas as pd
import matplotlib.pyplot as plt

# Out path constant
out_path = os.environ['HOME'] + "/out/"

###########################################################

def visualize_typecount(data):
    plt.figure(figsize = (18, 12))
    plt.pie(data["count"].values.tolist(),
            labels = data["type"].values.tolist(),
            autopct=lambda a : round(a, 3))
    plt.title("Usage of each vote's type (in %)")
    plt.savefig(out_path + "type-count.png", dpi = 450)
    plt.figure().clear()

# Reading typecount
typecount = pd.read_csv(out_path + "type-count.csv")

# Visualizing type count
visualize_typecount(typecount)

###########################################################

def addlabels(x,y):
    for i in range(len(x)):
        plt.text(i,y[i],y[i])

def visualize_votesbyusers(data):
    plt.figure(figsize = (18, 12))
    x = data["name"].values.tolist()
    y = data["count of votes done by user"].values.tolist()
    plt.bar(x, y)
    addlabels(x, y)
    plt.title("Votes done by users")
    plt.xlabel("Users")
    plt.ylabel("Vote count")
    plt.savefig(out_path + "votes-by-users.png", dpi = 450)
    plt.figure().clear()

# Reading votes by users
votesbyusers = pd.read_csv(out_path + "votes-by-users.csv")

# Visualizing votes by users
visualize_votesbyusers(votesbyusers)

###########################################################

def visualize_postscomments(data):
    plt.figure(figsize = (18, 12))
    x = data["post count"].values.tolist()
    y = data["comment count"].values.tolist()
    x = sorted(x)
    y = sorted(y)
    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.spines['left'].set_position('zero')
    ax.spines['bottom'].set_position('zero')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.xaxis.set_ticks_position('bottom')
    ax.yaxis.set_ticks_position('left')
    plt.title("Functional dependency: post count by user -> comment count by user")
    plt.plot(x,y)
    plt.savefig(out_path + "posts-comments.png", dpi = 450)
    plt.figure().clear()

# Reading post comments
postscomments = pd.read_csv(out_path + "posts-comments.csv")

# Visualizing posts comments
visualize_postscomments(postscomments)