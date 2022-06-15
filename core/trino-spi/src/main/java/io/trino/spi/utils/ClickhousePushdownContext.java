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

package io.trino.spi.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickhousePushdownContext
{
    public static Map<String, ClickhousePushdownContext> instances = new HashMap<>();
    private String pushdownSQL;
    private List<String> columns;

    public static void create(String queryId)
    {
        instances.put(queryId, new ClickhousePushdownContext());
    }

    public static ClickhousePushdownContext get(String queryId)
    {
        return instances.get(queryId);
    }

    public static void clear(String queryId)
    {
        instances.remove(queryId);
    }

    public List<String> getColumns()
    {
        return columns;
    }

    public void setColumns(List<String> columns)
    {
        this.columns = columns;
    }

    public String getPushdownSQL()
    {
        return pushdownSQL;
    }

    public void setPushdownSQL(String pushdownSQL)
    {
        this.pushdownSQL = pushdownSQL;
    }
}
