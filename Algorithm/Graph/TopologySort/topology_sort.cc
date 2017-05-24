#include <iostream>
#include <vector>
#include <deque>
#include <map>

// Key:   vertex number
// Value: vector of end points
using AdjList = std::map<int, std::vector<int>>;

// Key:   vertex number
// Value: indegree of vertex
using VertexList = std::map<int, int>;

AdjList CreateAdjList() {
    AdjList adj_list;

    int num_lines;
    std::cin >> num_lines;

    int u, v;
    for (int i = 0; i < num_lines; ++i) {
	std::cin >> u >> v;
        if (adj_list.find(u) == adj_list.end()) {
            adj_list.insert(std::make_pair(u, std::vector<int>()));
        }
        adj_list[u].push_back(v);
    }

    return adj_list;
}

void topology_sort(const AdjList& adj_list) {
	// Build vertex list.
	VertexList vertex_list;
	for (const auto& adj : adj_list) {
		// Add start point to vertex list.
		const int vertex_num = adj.first;
		vertex_list.insert(std::make_pair(vertex_num, 0));

		const std::vector<int>& end_points = adj.second;
		for (const int end_point : end_points) {
			// Add end point to vertex list.
			vertex_list.insert(std::make_pair(end_point, 0));
			// Also update indegree of end point.
			vertex_list.at(end_point)++;
		}
	}

	std::deque<int> queue;
	// Initialize zero indegree queue.
	for (const auto& vertex : vertex_list) {
		const int vertex_num = vertex.first;
		const int vertex_indegree = vertex.second;
		if (vertex_indegree == 0) {
			queue.push_back(vertex_num);
		}
	}
	// Start sorting.
	std::vector<int> sort_results;
	while (!queue.empty()) {
		// Add a zero-indegree vertex to result.
		const int vertex_num = queue.front();
		queue.pop_front();
		sort_results.push_back(vertex_num);

		// Decrement indegree of all related end points.
		// If not exists in adjancent list, then no end point exists
		// for this vertex.
		if (adj_list.find(vertex_num) != adj_list.end()) {
			for (const int end_point : adj_list.at(vertex_num)) {
				const int indegree = --vertex_list.at(end_point);
				if (indegree == 0) {
					queue.push_back(end_point);
				} else if (indegree < 0) {
					std::cout << "Serious bug happen.\n";
					return;
				}
			}
		}
	}

	if (sort_results.size() != vertex_list.size()) {
		std::cout << "Error, cycle detected.\n";
	}

	for (const int sort_result : sort_results) {
		std::cout << sort_result << " ";
	}
	std::cout << std::endl;
}

int main() {

    AdjList adj_list = CreateAdjList();

    topology_sort(adj_list);

    return 0;
}
