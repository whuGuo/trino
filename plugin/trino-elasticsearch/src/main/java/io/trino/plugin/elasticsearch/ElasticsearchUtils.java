/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.trino.plugin.elasticsearch;

public class ElasticsearchUtils
{
    private ElasticsearchUtils()
    {
    }

    private static boolean isAggregations(String query)
    {
        if (query.contains("\"aggs\"") || query.contains("\"aggregations\"")) {
            return true;
        }
        return false;
    }

    public static boolean isSplit(boolean isPassthroughQuerySplitEnable, String query)
    {
        return isPassthroughQuerySplitEnable && !isAggregations(query);
    }
}