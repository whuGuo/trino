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

package io.trino.sql.planner.iterative.rule;

import io.trino.matching.Captures;
import io.trino.matching.Pattern;
import io.trino.metadata.Metadata;
import io.trino.sql.planner.iterative.Rule;
import io.trino.sql.planner.plan.TableScanNode;

import static io.trino.sql.planner.plan.Patterns.tableScan;

public class ClickHouseSqlPushdown
        implements Rule<TableScanNode>
{
    private static final Pattern<TableScanNode> PATTERN = tableScan()
            .matching(tableScanNode ->
                    tableScanNode.getTable().getCatalogName().getCatalogName().equals("clickhouse")
                            && tableScanNode.getTable().toString().contains("pushdown_table")
                            && !tableScanNode.isClickHousePushdown());

    private final Metadata metadata;

    public ClickHouseSqlPushdown(Metadata metadata)
    {
        this.metadata = metadata;
    }

    @Override
    public Pattern<TableScanNode> getPattern()
    {
        return PATTERN;
    }

    @Override
    public Result apply(TableScanNode node, Captures captures, Context context)
    {
        metadata.applyClickHouseSqlPushdown(node.getTable().getConnectorHandle(), context.getSession(), node.getTable().getCatalogName());
        node.setClickHousePushdown(true);
        return Result.ofPlanNode(node);
    }
}
